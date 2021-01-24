package ts7.learning.redisapp.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import ts7.learning.redisapp.model.CountryScore;
import ts7.learning.redisapp.model.GeoLocationInfo;
import ts7.learning.redisapp.model.ReferrerScore;

@Component
public class Utilities {

	private static Logger logger = LoggerFactory.getLogger(Utilities.class);

	private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	/**
	 * This method is used for getting the correct IP address even if the client or
	 * devices is behind the proxy IP address
	 * 
	 * @return
	 */
	public static String getClientIpAddress(HttpServletRequest servletRequest) {
		String ip = "";
		for (String header : IP_HEADER_CANDIDATES) {
			ip = servletRequest.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				logger.info("getClientIpAddress " + ip);
				return ip;
			}
		}
		ip = servletRequest.getRemoteAddr();
		logger.info("getClientIpAddress getRemoteAddr " + ip);

		return ip;
	}

	/**
	 * This method will help us in getting the GeoLocation information.
	 * 
	 * @param ipAddressName
	 * @return
	 * @throws IOException
	 * @throws GeoIp2Exception
	 */
	public static GeoLocationInfo getCountryByIPAddress(String ipAddressName) throws IOException, GeoIp2Exception {
		GeoLocationInfo information = new GeoLocationInfo();
		String dbLocation = "GeoLite2-City.mmdb";

		File database = new File(dbLocation);
		DatabaseReader dbReader = new DatabaseReader.Builder(database).build();

		InetAddress ipAddress = InetAddress.getByName(ipAddressName);
		CityResponse response = dbReader.city(ipAddress);

		String countryName = response.getCountry().getName();
		String cityName = response.getCity().getName();

		response.getLocation().getLongitude();
		information.setCity(cityName);
		information.setCountry(countryName);
		information.setLatitude(response.getLocation().getLatitude());
		information.setLongitude(response.getLocation().getLongitude());
		information.setState(response.getLeastSpecificSubdivision().getName());
		logger.info("GeoLocationInfo info : " + information);
		return information;
	}

	/**
	 * This method is used for adding the referrer count by the tiny url ID.
	 * 
	 * @param redisTemplate
	 * @param keyTinyUrl
	 * @param referrerDomain
	 */
	public void saveReferrerScoreByKeys(StringRedisTemplate redisTemplate, String keyTinyUrl, String referrerDomain) {
		String key = null;

		// Key is created by appending the Table Name and state separated by |
		key = "tinyUrl-" + keyTinyUrl + "-referrer";
		Long getValue = redisTemplate.opsForZSet().zCard(key);
		logger.info("getValue " + getValue + " key is " + key);
		if (getValue != null && getValue > 0) {
			Double getCount = redisTemplate.opsForZSet().score(key, referrerDomain);
			if (getCount == null) {
				redisTemplate.opsForZSet().add(key, referrerDomain, new Integer(1));
				logger.info("Added the key " + key + " for " + referrerDomain);
			} else {
				logger.info("getCount is " + getCount.doubleValue());
				redisTemplate.opsForZSet().incrementScore(key, referrerDomain, 1);
			}
			logger.info("Incremented here. Alhamdulilaah");
		} else {
			redisTemplate.opsForZSet().add(key, referrerDomain, new Integer(1));
		}
	}

	/**
	 * This method is used for adding the country country by tiny url
	 * 
	 * @param redisTemplate
	 * @param keyTinyUrl
	 * @param country
	 */
	public void saveCountryByKeys(StringRedisTemplate redisTemplate, String keyTinyUrl, String country) {
		String key = null;

		// Key is created by appending the Table Name and state separated by |
		key = "tinyUrl-" + keyTinyUrl + "-country";
		Long getValue = redisTemplate.opsForZSet().zCard(key);
		logger.info("getValue " + getValue + " key is " + key);
		if (getValue != null && getValue > 0) {
			Double getCount = redisTemplate.opsForZSet().score(key, country);
			if (getCount == null) {
				redisTemplate.opsForZSet().add(key, country, new Integer(1));
				logger.info("Added the key " + key + " for " + country);
			} else {
				logger.info("getCount is " + getCount.doubleValue());
				redisTemplate.opsForZSet().incrementScore(key, country, 1);
			}
			logger.info("Incremented here. Alhamdulilaah");
		} else {
			redisTemplate.opsForZSet().add(key, country, new Integer(1));
		}
	}

	/**
	 * This method is used for getting the country score by tiny url
	 * 
	 * @param redisTemplate
	 * @param keyTinyUrl
	 * @param country
	 */
	public List<CountryScore> getCountryScoreByKeys(StringRedisTemplate redisTemplate, String keyTinyUrl) {
		String key = null;
		key = "tinyUrl-" + keyTinyUrl + "-country";

		List<CountryScore> scoreList = new ArrayList<CountryScore>();
		// Key is created by appending the Table Name and state separated by |
		Set<TypedTuple<String>> getTuples = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
		if (getTuples != null && getTuples.size() > 0) {
			for (TypedTuple<String> instance : getTuples) {
				CountryScore scoreBean = new CountryScore();
				scoreBean.setCountry(instance.getValue());
				scoreBean.setCountryScore(instance.getScore().doubleValue());
				logger.info("Added the country " + scoreBean);
				scoreList.add(scoreBean);
			}
		}
		return scoreList;
	}

	/**
	 * This method is used for getting the referrer score by tiny url
	 * 
	 * @param redisTemplate
	 * @param keyTinyUrl
	 * @param country
	 */
	public List<ReferrerScore> getReferrerScoreByKeys(StringRedisTemplate redisTemplate, String keyTinyUrl) {
		String key = null;
		key = "tinyUrl-" + keyTinyUrl + "-referrer";

		List<ReferrerScore> scoreList = new ArrayList<ReferrerScore>();
		// Key is created by appending the Table Name and state separated by |
		Set<TypedTuple<String>> getTuples = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
		if (getTuples != null && getTuples.size() > 0) {
			for (TypedTuple<String> instance : getTuples) {
				ReferrerScore scoreBean = new ReferrerScore();

				scoreBean.setReferrer(instance.getValue());
				scoreBean.setReferrerScore(instance.getScore().doubleValue());
				logger.info("Added the referrer " + scoreBean);
				scoreList.add(scoreBean);
			}
		}
		return scoreList;
	}

}
