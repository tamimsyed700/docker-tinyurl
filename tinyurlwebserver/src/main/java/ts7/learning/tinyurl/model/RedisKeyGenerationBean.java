package ts7.learning.tinyurl.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RedisKeyGenerationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tinyUrlId;
	private String userId;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String url;

	private boolean isActive;

	public String getTinyUrlId() {
		return tinyUrlId;
	}

	public void setTinyUrlId(String tinyUrlId) {
		this.tinyUrlId = tinyUrlId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "RedisKeyGenerationBean [tinyUrlId=" + tinyUrlId + ", userId=" + userId + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", url=" + url + ", isActive=" + isActive + "]";
	}

}
