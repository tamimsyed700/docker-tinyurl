package ts7.learning.redisapp.model;

/**
 * This will hold the list of referrer score list.
 * 
 * @author tamim
 *
 */
public class ReferrerScore {

	private String tinrUrl;

	public String getTinrUrl() {
		return tinrUrl;
	}

	public void setTinrUrl(String tinrUrl) {
		this.tinrUrl = tinrUrl;
	}

	private String referrer;
	private double referrerScore;

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public double getReferrerScore() {
		return referrerScore;
	}

	public void setReferrerScore(double referrerScore) {
		this.referrerScore = referrerScore;
	}

	@Override
	public String toString() {
		return "ReferrerScoreList [tinrUrl=" + tinrUrl + ", referrer=" + referrer + ", referrerScore=" + referrerScore
				+ "]";
	}

}
