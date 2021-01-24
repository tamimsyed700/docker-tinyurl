package ts7.learning.tinyurl.testing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import ts7.learning.tinyurl.model.NewTinyUrlRequest;
import ts7.learning.tinyurl.model.TinyUrlResponse;
import ts7.learning.tinyurl.services.TinyUrlInfoServices;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TinyUrlRepositoryTest {

	@Autowired
	private TinyUrlInfoServices tinyUrlServices;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testPersistence() throws Exception {

		String newRequestJSON = "{\r\n" + "    \"userId\": \"Tamim\",\r\n" + "    \"apiKey\": \"TamimApiKey\",\r\n"
				+ "    \"secretKey\": \"TamimSecretKey\",\r\n"
				+ "    \"url\": \"https://imuslim.xyz/article/islam-loves-jesus-christ-peace-be-upon-him.html\"\r\n"
				+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		NewTinyUrlRequest newObjectReqquest = objectMapper.readValue(newRequestJSON, NewTinyUrlRequest.class);
		TinyUrlResponse response = tinyUrlServices.saveOrUpdate(newObjectReqquest);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getTinyUrlId());
		Assert.assertEquals(response.isActive(), false);
	}
}