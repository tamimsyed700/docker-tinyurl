package ts7.learning.kgstesting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import ts7.learning.redisapp.TinyUrlRedisApp;
import ts7.learning.redisapp.model.NewTinyUrlRequest;
import ts7.learning.redisapp.model.NewTinyUrlResponse;
import ts7.learning.redisapp.model.RedisKeyGenerationBean;
import ts7.learning.redisapp.model.repository.KeyGenerationURLRepo;
import ts7.learning.redisapp.services.RedisTinyUrlServices;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TinyUrlRedisApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TinyUrlKGSIntegrationTesting {

	private Logger logger = LoggerFactory.getLogger(TinyUrlKGSIntegrationTesting.class);

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Mock
	private RedisTinyUrlServices redisServices;

	@Mock
	private KeyGenerationURLRepo kgsRepo;

	@Mock
	private StringRedisTemplate redisTemplate;

	@Mock
	private HttpServletResponse response;

	@Mock
	private HttpServletRequest request;

	@Test
	public void testPostKGSKey() throws Exception {
		String jsonBody = "{\r\n" + "    \"userId\": \"Tamim\",\r\n" + "    \"apiKey\": \"TamimApiKey\",\r\n"
				+ "    \"secretKey\": \"TamimSecretKey\",\r\n"
				+ "    \"url\":\"https://imuslim.xyz/article/islam-loves-jesus-christ-peace-be-upon-him.html\"\r\n"
				+ "}";
		logger.info(jsonBody); 
		ObjectMapper mapper = new ObjectMapper();
		NewTinyUrlRequest getRequestBody = mapper.readValue(jsonBody, NewTinyUrlRequest.class);
		ResponseEntity<NewTinyUrlResponse> getResponse = restTemplate
				.postForEntity("http://localhost:9001/createurlkey", getRequestBody, NewTinyUrlResponse.class);
		NewTinyUrlResponse response = getResponse.getBody();
		logger.info("testPostKGSKey Response : " + response);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTinyUrlId());
		Assert.assertNotNull(response.getUrl());
		Assert.assertFalse(response.isActive());
		Assert.assertNotNull(response.getTinyUrlId());
		Assert.assertTrue((response.getNumberOfTimesHit() == 0));
		if (getResponse != null) {
			Assert.assertEquals(200, getResponse.getStatusCodeValue());
		}
	}

	@Test
	public void testGetKGSKey() throws Exception {

		List<RedisKeyGenerationBean> redisKeyGenerationBean = kgsRepo.findByIsActive(false);

		Assert.assertNotNull(redisKeyGenerationBean);
		if (redisKeyGenerationBean != null && redisKeyGenerationBean.size() > 0) {
			RedisKeyGenerationBean beanInfo = redisKeyGenerationBean.get(0);
			Assert.assertNotNull(beanInfo);
			if (beanInfo != null) {
				logger.info("testGetKGSKey RedisKeyGenerationBean : " + beanInfo);
				HttpHeaders headers = new HttpHeaders();
				headers.set("Referer", "http://www.twitter.com");
				headers.set("X-Forwarded-For", "45.252.191.0");
				HttpEntity<NewTinyUrlResponse> httpResponseEntity = new HttpEntity<NewTinyUrlResponse>(headers);
				ResponseEntity<NewTinyUrlResponse> responseEntity = restTemplate.exchange(
						"http://localhost:9001/" + beanInfo.getTinyUrlId(), HttpMethod.GET, httpResponseEntity,
						NewTinyUrlResponse.class);
				NewTinyUrlResponse response = responseEntity.getBody();
				if (response != null) {
					logger.info("testGetKGSKey Response : " + response);

					Assert.assertNotNull(response.getUrl());
					Assert.assertNotNull(response.getTinyUrlId());
					Assert.assertTrue(response.getNumberOfTimesHit() > 0);

					Assert.assertNotNull(response.getCountryList());
					Assert.assertNotNull(response.getReferrerScoreList());
					Assert.assertTrue(response.getCountryList().size() > 0);

					Assert.assertTrue(response.getReferrerScoreList().size() > 0);
					Assert.assertFalse(response.isActive());
				}
			}
		}

	}
}
