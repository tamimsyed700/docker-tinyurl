package ts7.learning.redisapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("tinyUrl")
public class RedisKeyGenerationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String tinyUrlId;
	private String userId;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String url;
	private float numberOfTimesHit;

	@Indexed
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public float getNumberOfTimesHit() {
		return numberOfTimesHit;
	}

	public void setNumberOfTimesHit(float numberOfTimesHit) {
		this.numberOfTimesHit = numberOfTimesHit;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "RedisKeyGenerationBean [tinyUrlId=" + tinyUrlId + ", userId=" + userId + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", url=" + url + ", numberOfTimesHit=" + numberOfTimesHit
				+ ", isActive=" + isActive + "]";
	}

}
