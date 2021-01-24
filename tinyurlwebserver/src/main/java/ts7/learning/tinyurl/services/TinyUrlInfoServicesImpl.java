package ts7.learning.tinyurl.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.id.IdentifierGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import ts7.learning.tinyurl.model.NewTinyUrlRequest;
import ts7.learning.tinyurl.model.TinyUrlKeyInfo;
import ts7.learning.tinyurl.model.TinyUrlResponse;
import ts7.learning.tinyurl.repository.TinyUrlRepository;
import ts7.learning.tinyurl.services.util.Utility;

/**
 * This services wrapping the database functionality
 * 
 * @author tamim
 *
 */
@Service
public class TinyUrlInfoServicesImpl implements TinyUrlInfoServices {

	private Logger logger = LoggerFactory.getLogger(TinyUrlInfoServices.class);
	@Autowired
	private TinyUrlRepository tinyUrlRepository;

	@Override
	public List<TinyUrlKeyInfo> getAll() {
		List<TinyUrlKeyInfo> allTinyUrlBeans = new ArrayList<TinyUrlKeyInfo>();
		tinyUrlRepository.findAll().forEach(allTinyUrlBeans::add);
		return allTinyUrlBeans;
	}

	@Override
	public TinyUrlKeyInfo getById(String tinyUrlId) {
		if (tinyUrlRepository.existsById(tinyUrlId))
			return tinyUrlRepository.findById(tinyUrlId).get();
		else
			return new TinyUrlKeyInfo();
	}

	@Override
	public TinyUrlResponse saveOrUpdate(NewTinyUrlRequest saveInfo){
		TinyUrlKeyInfo saveTinyurlObject = new TinyUrlKeyInfo();

		TinyUrlResponse response = new TinyUrlResponse();
		// checking if the alias is sent by the user or not.
		if (saveInfo != null && !StringUtils.isEmpty(saveInfo.getUrlAlias())) {
			// if it is sent by the user then i will check for the alias is available or
			// not.
			boolean isValidURLAlias = Utility.isValidURLAlias(saveInfo.getUrlAlias());
			if (!isValidURLAlias)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid Alias URL.Please choose another alias url");
			// checking if alias is present or not. If not then i will store in the database
			// else it will throw the error to the database.
			if (tinyUrlRepository.existsById(saveInfo.getUrlAlias())) {
				throw new ResponseStatusException(HttpStatus.IM_USED, "The URL Alias already exist in the database.");
			}
			response.setTinyUrlId(saveInfo.getUrlAlias());

		} else {
			// If no alias is sent then i will get the available keys dynamically from the
			// database and send it to the user.
			List<TinyUrlKeyInfo> saveTinyUrlInfo = tinyUrlRepository.findByIsActive(true);
			if (saveTinyUrlInfo != null && saveTinyUrlInfo.size() > 0) {
				logger.info("Getting the active keys here ");
				saveTinyurlObject = saveTinyUrlInfo.get(0);
				logger.info("Getting the active keys  " + saveTinyurlObject.getTinyUrlId());

			}
		}
		// setting the mariadb object to save it in the db with update value.
		saveTinyurlObject.setUrl(saveInfo.getUrl());
		saveTinyurlObject.setUser(saveInfo.getUserId());
		saveTinyurlObject.setUpdatedTime(LocalDateTime.now());
		saveTinyurlObject.setActive(false);

		response.setUrl(saveInfo.getUrl());
		response.setActive(saveTinyurlObject.isActive());
		response.setTinyUrlId(saveTinyurlObject.getTinyUrlId());

		if (saveTinyurlObject != null) {
			try {
				tinyUrlRepository.save(saveTinyurlObject);
			} catch (IdentifierGenerationException ex) {
				logger.error("Error in the Tiny URL Repository : " + ex.getLocalizedMessage());
				throw new ResponseStatusException(HttpStatus.IM_USED, ex.getLocalizedMessage());
			}
		}
		// returning the user with the new tinyurl information.
		return response;
	}

}
