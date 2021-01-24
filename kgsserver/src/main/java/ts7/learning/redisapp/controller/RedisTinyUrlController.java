package ts7.learning.redisapp.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import ts7.learning.redisapp.model.NewTinyUrlRequest;
import ts7.learning.redisapp.model.NewTinyUrlResponse;
import ts7.learning.redisapp.services.RedisTinyUrlServices;

/**
 * This controller will be responsible for the CRUD operations on the Redis in
 * memory server.
 * 
 * @author tamim
 *
 */
@RestController
public class RedisTinyUrlController {

	protected Logger logger = LoggerFactory.getLogger(RedisTinyUrlController.class);

	@Autowired
	private RedisTinyUrlServices redisServices;

	/**
	 * This API will delete the tinyUrl entry from the redis database.
	 * 
	 * @param id
	 * @throws GeoIp2Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public NewTinyUrlResponse getUrlByKey(@RequestHeader("referrer") String referrer,
			@RequestHeader("user-agent") String userAgent, @PathVariable("id") String id)
			throws IOException, GeoIp2Exception {
		NewTinyUrlResponse responseBody = redisServices.getTinyUrl(referrer, userAgent, id);
		return responseBody;
	}

	/**
	 * This API is used for getting only the active keys for sending to the
	 * webserver request.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createurlkey", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody NewTinyUrlResponse createNewUrl(@RequestBody NewTinyUrlRequest request) {
		NewTinyUrlResponse responseBody = redisServices.createNewUrlOnRedis(request);
		return responseBody;
	}
}
