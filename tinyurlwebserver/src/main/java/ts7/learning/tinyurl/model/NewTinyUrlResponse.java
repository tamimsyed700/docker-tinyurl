package ts7.learning.tinyurl.model;

public class NewTinyUrlResponse extends RedisKeyGenerationBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewTinyUrlResponse() {
	}

	@Override
	public String toString() {
		return "NewTinyUrlResponse [getTinyUrlId()=" + getTinyUrlId() + ", getUserId()=" + getUserId()
				+ ", getCreatedTime()=" + getCreatedTime() + ", getUpdatedTime()=" + getUpdatedTime() + ", isActive()="
				+ isActive() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}

}
