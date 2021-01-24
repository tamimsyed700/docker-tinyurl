package ts7.learning.redisapp.model;

import java.util.List;

/**
 * 
 * The class will return the tiny url details from redis cache.
 * 
 * @author tamim
 *
 */
public class NewTinyUrlResponse extends RedisKeyGenerationBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewTinyUrlResponse() {
	}

	private List<CountryScore> countryList;

	private List<ReferrerScore> referrerScoreList;

	public List<CountryScore> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryScore> countryList) {
		this.countryList = countryList;
	}

	public List<ReferrerScore> getReferrerScoreList() {
		return referrerScoreList;
	}

	public void setReferrerScoreList(List<ReferrerScore> referrerScoreList) {
		this.referrerScoreList = referrerScoreList;
	}

	@Override
	public String toString() {
		return "NewTinyUrlResponse [countryReferrerList=" + countryList + ", referrerScoreList=" + referrerScoreList
				+ "]";
	}
}
