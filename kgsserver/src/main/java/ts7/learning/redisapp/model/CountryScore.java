package ts7.learning.redisapp.model;

/**
 * This will hold the list of country score list.
 * 
 * @author tamim
 *
 */
public class CountryScore {

	private String tinrUrl;

	public String getTinrUrl() {
		return tinrUrl;
	}

	public void setTinrUrl(String tinrUrl) {
		this.tinrUrl = tinrUrl;
	}

	private String country;
	private double countryScore;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getCountryScore() {
		return countryScore;
	}

	public void setCountryScore(double countryScore) {
		this.countryScore = countryScore;
	}

	@Override
	public String toString() {
		return "CountryScoreList [tinrUrl=" + tinrUrl + ", country=" + country + ", countryScore=" + countryScore + "]";
	}

}
