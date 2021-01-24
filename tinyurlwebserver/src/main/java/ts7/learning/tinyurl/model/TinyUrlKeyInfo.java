package ts7.learning.tinyurl.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the Entity model for the Maria DB Tiny Url Table.
 * 
 * @author tamim
 *
 */
@Entity	
public class TinyUrlKeyInfo {

	@Id
	private String tinyUrlId;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private boolean isActive;
	private String url;
	private String user;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTinyUrlId() {
		return tinyUrlId;
	}

	public void setTinyUrlId(String tinyUrlId) {
		this.tinyUrlId = tinyUrlId;
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

	@Override
	public String toString() {
		return "TinyUrlKeyInfo [tinyUrlId=" + tinyUrlId + ", createdTime=" + createdTime + ", updatedTime="
				+ updatedTime + ", isActive=" + isActive + ", url=" + url + ", user=" + user + "]";
	}
}
