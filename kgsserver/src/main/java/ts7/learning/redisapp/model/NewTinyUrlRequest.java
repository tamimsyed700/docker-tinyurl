package ts7.learning.redisapp.model;

import java.io.Serializable;

/**
 * This request will be invoked by the Webserver for checking out the active
 * tiny url request.
 * 
 * @author tamim
 *
 */
public class NewTinyUrlRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private String tinyUrl;
	private String userId;
	private String url;

	public String getUrl() {
		return url;
	}

	public String getTinyUrl() {
		return tinyUrl;
	}

	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String apiKey;
	private String secretKey;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public String toString() {
		return "NewTinyUrlRequest [tinyUrl=" + tinyUrl + ", userId=" + userId + ", url=" + url + ", apiKey=" + apiKey
				+ ", secretKey=" + secretKey + "]";
	}
}
