package ts7.learning.tinyurl.testing;

import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import ts7.learning.tinyurl.TinyUrlWebserverApplication;
import ts7.learning.tinyurl.model.NewTinyUrlRequest;
import ts7.learning.tinyurl.model.NewTinyUrlResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TinyUrlWebserverApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TinyUrlWebServerControllerTesting {

	private Logger logger = LoggerFactory.getLogger(TinyUrlWebServerControllerTesting.class);

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testPostWebServerTinyUrlRequest() throws Exception {
		// Hitting the URL with post to create new URL mapping on the Redis for the
		// TinyUrl System
		String jsonBody = "{\r\n" + "    \"userId\": \"Tamim\",\r\n" + "    \"apiKey\": \"TamimApiKey\",\r\n"
				+ "    \"secretKey\": \"TamimSecretKey\",\r\n"
				+ "    \"url\":\"https://imuslim.xyz/article/islam-loves-jesus-christ-peace-be-upon-him.html\"\r\n"
				+ "}";
		logger.info(jsonBody);
		ObjectMapper mapper = new ObjectMapper();
		// Converting the JSON to the NewTinyUrlRequest object
		NewTinyUrlRequest getRequestBody = mapper.readValue(jsonBody, NewTinyUrlRequest.class);
		// Getting the response in the Response Entity.
		ResponseEntity<NewTinyUrlResponse> getResponse = restTemplate.postForEntity("http://localhost:9000/",
				getRequestBody, NewTinyUrlResponse.class);
		// Checking if the response is not null
		Assert.assertNotNull(getResponse);
		// Getting the body of the response to get the tinyUrl Key and the URL mapping
		// for sending the redirect
		NewTinyUrlResponse tinyUrlResponse = getResponse.getBody();
		logger.info("testPostWebServerTinyUrlRequest Response : " + tinyUrlResponse);
		if (getResponse != null) {
			Assert.assertEquals(200, getResponse.getStatusCodeValue());
		}
		HttpClient instance = HttpClientBuilder.create().build();
		String url = "http://localhost:9000/" + tinyUrlResponse.getTinyUrlId();

		HttpResponse response = instance.execute(new HttpGet(url));
		logger.info("Response URL : " + url + " redirected to the " + tinyUrlResponse.getUrl());
		Assert.assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	}
}
