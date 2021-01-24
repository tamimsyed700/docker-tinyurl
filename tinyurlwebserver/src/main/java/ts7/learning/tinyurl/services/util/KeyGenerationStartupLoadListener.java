package ts7.learning.tinyurl.services.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import ts7.learning.tinyurl.model.TinyUrlKeyInfo;
import ts7.learning.tinyurl.repository.TinyUrlRepository;
import ts7.learning.tinyurl.repository.config.KeyGenerationConfig;

/**
 * This class will be used for generating the Key Generation service. It will
 * load the unique keys into the text file. The same file will be used for
 * loading into the redis memory
 * 
 * @author tamim
 *
 */
@Component
public class KeyGenerationStartupLoadListener implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(KeyGenerationStartupLoadListener.class);

	private long startTime, endTime = 0L;
	@Autowired
	private KeyGenerationConfig config;

	@Autowired
	private TinyUrlRepository kgsRepo;

	public static int counter;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("KeyGenerationStartupLoadListener started ..");
		startTime = System.currentTimeMillis();
		writeToRedisCache();
		endTime = (System.currentTimeMillis() - startTime) / 1000;
		logger.info("Time taken to load in MariaDB Server: " + endTime + " seconds ");
	}

	public void writeToRedisCache() throws InterruptedException, IOException {

		logger.info(config.toString());
		List<TinyUrlKeyInfo> listKeyValuBeans = new ArrayList<TinyUrlKeyInfo>();
		try {

			// Initializing the Random object to randomly generate the keys.
			Random random = new Random();

			// Specifying the character set for the key generation system
			char[] alphabet = config.getCharSet().toCharArray();

			// specifying the length of the key.
			int size = config.getSizeOfKeys();

			// Generating 10 milion keys for the Redis cache.
			for (int i = 0; i < config.getNumberOfKeys(); i++) {
				String id = NanoIdUtils.randomNanoId(random, alphabet, size);// Gwvyxk
				TinyUrlKeyInfo keyValueBean = new TinyUrlKeyInfo();
				keyValueBean.setActive(true);
				keyValueBean.setCreatedTime(LocalDateTime.now());
				keyValueBean.setTinyUrlId(id);
				listKeyValuBeans.add(keyValueBean);
			}
			for (TinyUrlKeyInfo keyGenerationRepo : listKeyValuBeans)
				kgsRepo.save(keyGenerationRepo);
		} catch (Exception exception) {
			logger.error("Oh no some error happened while writing it to the Redis." + exception.fillInStackTrace());
			throw exception;
		}
	}
}