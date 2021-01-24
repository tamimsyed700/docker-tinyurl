package ts7.learning.redisapp.model;

/**
 * The class will be used for getting and setting the geo location information
 * such as City,Country, State etc.,
 * 
 * @author tamim
 *
 */
public class GeoLocationInfo {

	private double latitude;
	private double longitude;
	private String country;
	private String city;
	private String postalCode;
	private String state;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "GeoLocationInfo [latitude=" + latitude + ", longitude=" + longitude + ", country=" + country + ", city="
				+ city + ", postalCode=" + postalCode + ", state=" + state + "]";
	}

}
