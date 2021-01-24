package ts7.learning.redisapp.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import ts7.learning.redisapp.model.CountryScore;
import ts7.learning.redisapp.model.GeoLocationInfo;
import ts7.learning.redisapp.model.NewTinyUrlRequest;
import ts7.learning.redisapp.model.NewTinyUrlResponse;
import ts7.learning.redisapp.model.RedisKeyGenerationBean;
import ts7.learning.redisapp.model.ReferrerScore;
import ts7.learning.redisapp.model.repository.KeyGenerationURLRepo;
import ts7.learning.redisapp.util.Utilities;

/**
 * 
 * This service will make actual work for interacting with the Redis cache.
 * 
 * @author tamim
 *
 */
@Service
public class RedisTinyUrlServices {

	@Autowired
	private KeyGenerationURLRepo kgsRepo;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpServletRequest request;

	private Logger logger = LoggerFactory.getLogger(RedisTinyUrlServices.class);

	public RedisTinyUrlServices() {
	}

	public NewTinyUrlResponse getTinyUrl(String referrer, String userAgent, String tinyUrlId)
			throws IOException, GeoIp2Exception {
		NewTinyUrlResponse responseBody = new NewTinyUrlResponse();
		RedisKeyGenerationBean infoBean = null;
		try {
			if (kgsRepo.existsById(tinyUrlId)) {
				infoBean = kgsRepo.findById(tinyUrlId).get();
				String ipAddress = Utilities.getClientIpAddress(request);

				logger.info("IP Address is " + ipAddress);				
				GeoLocationInfo locationInfo = null;
				if (ipAddress!=null && !"127.0.0.1".equalsIgnoreCase(ipAddress))
					locationInfo = Utilities.getCountryByIPAddress(ipAddress);
				logger.info("GeoLocation information is " + locationInfo);

				if (referrer != null && referrer.length() > 0) {
					new Utilities().saveReferrerScoreByKeys(redisTemplate,tinyUrlId, referrer);
					logger.info("Adding the referrer into the Redis.");
				}

				if (locationInfo != null && locationInfo.getCountry() != null) {
					new Utilities().saveCountryByKeys(redisTemplate, tinyUrlId,
							locationInfo.getCountry());
					logger.info("Adding the country into the Redis.");

				}
				float totalNoOfTimes = infoBean.getNumberOfTimesHit();
				totalNoOfTimes++;
				infoBean.setNumberOfTimesHit(totalNoOfTimes);
				// Getting list of scores here.
				List<CountryScore> countryScoreList = new Utilities().getCountryScoreByKeys(redisTemplate,
						tinyUrlId);
				List<ReferrerScore> referrerScoreList = new Utilities().getReferrerScoreByKeys(redisTemplate,
						tinyUrlId);
				responseBody.setCountryList(countryScoreList);
				responseBody.setReferrerScoreList(referrerScoreList);

				responseBody.setNumberOfTimesHit(totalNoOfTimes);
				responseBody.setTinyUrlId(tinyUrlId);

				responseBody.setCreatedTime(infoBean.getCreatedTime());
				responseBody.setUpdatedTime(infoBean.getUpdatedTime());

				responseBody.setUrl(infoBean.getUrl());
				responseBody.setUserId(infoBean.getUserId());
				kgsRepo.save(infoBean);
				logger.info("TinyUrl status is " + infoBean);
			} else {
				response.setStatus(204);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.error(e.getLocalizedMessage());
			e.fillInStackTrace();
		}
		return responseBody;
	}

	public NewTinyUrlResponse createNewUrlOnRedis(NewTinyUrlRequest request) {
		long startTime = System.currentTimeMillis();
		NewTinyUrlResponse responseBody = new NewTinyUrlResponse();
		// TODO: Need to authenticate the user here.
		// List<RedisKeyGenerationBean> getAllRedisKeyGenBean =
		// kgsRepo.findByIsActive(true);
//		logger.info(getAllRedisKeyGenBean.get(0)+" Am here ????: ");
//		if (getAllRedisKeyGenBean != null && getAllRedisKeyGenBean.size() > 0) {
		RedisKeyGenerationBean redisKeyBeanInfo = new RedisKeyGenerationBean();

		// if (redisKeyBeanInfo != null) {
		redisKeyBeanInfo.setActive(false);
		redisKeyBeanInfo.setTinyUrlId(request.getTinyUrl());
		redisKeyBeanInfo.setUserId(request.getUserId());

		redisKeyBeanInfo.setUrl(request.getUrl());
		responseBody.setTinyUrlId(redisKeyBeanInfo.getTinyUrlId());

		responseBody.setCreatedTime(redisKeyBeanInfo.getCreatedTime());
		responseBody.setActive(redisKeyBeanInfo.isActive());

		responseBody.setUrl(request.getUrl());
		responseBody.setUserId(redisKeyBeanInfo.getUserId());

		redisKeyBeanInfo.setUpdatedTime(LocalDateTime.now());
		kgsRepo.save(redisKeyBeanInfo);
		logger.info("Sending the tinyUrl " + responseBody.getTinyUrlId() + " and updating the redis data structure "
				+ redisKeyBeanInfo.toString());
//			} else {
//				logger.info("No active tiny url keys found");
//				response.setStatus(204);
//			}
//		} else {
//			logger.info("No active tiny url keys found");
//			response.setStatus(204);
//		}
		long endTime = startTime - System.currentTimeMillis() / 1000;
		response.setHeader("TimeTakenByWebServer(seconds)", endTime + "");
		return responseBody;
	}
}
