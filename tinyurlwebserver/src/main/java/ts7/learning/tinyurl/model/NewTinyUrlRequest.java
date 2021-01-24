package ts7.learning.tinyurl.model;

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
	private String userId;
	private String url;
	private String urlAlias;
	private String apiKey;
	private String secretKey;
	private String tinyUrl;

	public String getTinyUrl() {
		return tinyUrl;
	}

	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlAlias() {
		return urlAlias;
	}

	public void setUrlAlias(String urlAlias) {
		this.urlAlias = urlAlias;
	}

	@Override
	public String toString() {
		return "NewTinyUrlRequest [userId=" + userId + ", url=" + url + ", urlAlias=" + urlAlias + ", apiKey=" + apiKey
				+ ", secretKey=" + secretKey + ", tinyUrl=" + tinyUrl + "]";
	}
}
