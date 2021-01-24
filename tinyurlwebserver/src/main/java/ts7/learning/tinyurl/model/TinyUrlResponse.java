package ts7.learning.tinyurl.model;

/**
 * Response JSON structure for the TinyURL Webserver controller.
 * 
 * @author tamim
 *
 */
public class TinyUrlResponse extends TinyUrlKeyInfo {

	public TinyUrlResponse() {
	}

	@Override
	public String toString() {
		return "TinyUrlResponse [getUrl()=" + getUrl() + ", getUser()=" + getUser() + ", getTinyUrlId()="
				+ getTinyUrlId() + ", getCreatedTime()=" + getCreatedTime() + ", getUpdatedTime()=" + getUpdatedTime()
				+ ", isActive()=" + isActive() + "]";
	}

}
