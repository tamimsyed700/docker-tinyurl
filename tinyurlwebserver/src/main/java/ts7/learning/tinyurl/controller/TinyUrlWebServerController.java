package ts7.learning.tinyurl.controller;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import ts7.learning.tinyurl.model.NewTinyUrlRequest;
import ts7.learning.tinyurl.model.NewTinyUrlResponse;
import ts7.learning.tinyurl.model.RedisKeyGenerationBean;
import ts7.learning.tinyurl.model.TinyUrlResponse;
import ts7.learning.tinyurl.services.TUExecutorThreadService;
import ts7.learning.tinyurl.services.TinyUrlInfoServices;
import ts7.learning.tinyurl.services.util.Utility;

@RestController
public class TinyUrlWebServerController {

	private Logger logger = Logger.getLogger(TinyUrlWebServerController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${tinyurl.kgs.url}")
	private String kgsUrl;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private TinyUrlInfoServices tinyUrlServices;

	@Autowired
	private TUExecutorThreadService executorThreadService;

	/**
	 * This API will let the end user create the tiny url mapping
	 * 
	 * @param request
	 * @return
	 * @throws NoURLAvailableErrorException
	 */
	@RequestMapping(value = "/createurlkey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody TinyUrlResponse createURL(@RequestBody NewTinyUrlRequest request) {
		long startTime = System.currentTimeMillis();
		final String uri = kgsUrl + "/createurlkey";
		logger.info("URL for the KGS service is " + uri);

		// I will check if the alias is present or not and then update the database
		// accordingly in the below method.
		TinyUrlResponse tinyUrlResponseObject = null;
		try {
			tinyUrlResponseObject = tinyUrlServices.saveOrUpdate(request);
		} catch (Exception ex) {
			logger.error("createURL controller " + ex.getLocalizedMessage());
			throw new ResponseStatusException(HttpStatus.IM_USED, ex.getLocalizedMessage());
		}
		logger.info("tinyUrlResponseObject " + tinyUrlResponseObject);
		request.setTinyUrl(tinyUrlResponseObject.getTinyUrlId());
		// Once the database is updated then i will move the active keys to the Redis
		// for fast retrieval during the GetKey operation.
		// TODO: If the keys is expired in the Redis then we need to have a mechanism to
		// load the inactive key back to the database.

		Future<NewTinyUrlResponse> redisPostResponse = executorThreadService
				.executeAsynTask(new Callable<NewTinyUrlResponse>() {

					@Override
					public NewTinyUrlResponse call() throws Exception {
						NewTinyUrlResponse redisPostResponse = restTemplate.postForObject(uri, request,
								NewTinyUrlResponse.class);
						logger.info("Executor got executed here " + redisPostResponse);
						return redisPostResponse;
					}
				});
		// NewTinyUrlResponse redisPostResponse = restTemplate.postForObject(uri,
		// request, NewTinyUrlResponse.class);
		if (redisPostResponse != null) {
			logger.info("Bean information from Future " + tinyUrlResponseObject);
		} else {
			response.setStatus(HttpStatus.NO_CONTENT.value());
		}
		// long endTime = startTime - System.currentTimeMillis() / 1000;
		// response.setHeader("TimeTakenByWebServer(seconds)", endTime + "");
		return tinyUrlResponseObject;
	}

	/**
	 * This API will let the user to get the URL redirect(if it is available
	 * otherwise 204) for the given tiny url key.
	 * 
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void getTinryUrl(@PathVariable("id") String id) throws Exception {
		// long startTime = System.currentTimeMillis();
		HttpHeaders headers = new HttpHeaders();

		final String uri = kgsUrl + "/" + id;
		try {
			/* TODO: This needs to come from discovery service such as Eureka */
			logger.info("URL for the KGS service is " + uri);

			Map<String, String> headerMap = Utility.getHeadersInfo(httpRequest);

			String referrer = headerMap.get("referrer");
			referrer = (referrer == null) ? "Direct" : referrer;
			headers.set("referrer", referrer);
			String xForwardedFor = headerMap.get("x-forwarded-for");
			xForwardedFor = (xForwardedFor == null) ? "127.0.0.1" : xForwardedFor;
			xForwardedFor = ("0:0:0:0:0:0:0:1".equalsIgnoreCase(xForwardedFor)) ? "127.0.0.1" : xForwardedFor;
			headers.set("x-forwarded-for", xForwardedFor);

			HttpEntity<RedisKeyGenerationBean> httpHeaders = new HttpEntity<RedisKeyGenerationBean>(null, headers);
			ResponseEntity<RedisKeyGenerationBean> respEntity = restTemplate.exchange(uri, HttpMethod.GET, httpHeaders,
					RedisKeyGenerationBean.class);
			RedisKeyGenerationBean redirectUrl = respEntity.getBody();
			if (redirectUrl != null) {
				logger.info("RedirectUrl information " + redirectUrl);
				response.setHeader("Location", redirectUrl.getUrl());
				// long endTime = startTime - System.currentTimeMillis() / 1000;
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			} else
				response.setStatus(HttpStatus.NO_CONTENT.value());
		} catch (Exception e) {
			logger.error(e.fillInStackTrace());
		}
	}
}