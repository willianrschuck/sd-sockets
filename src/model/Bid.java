package model;

public class Bid {

	private static final char SEPARATOR = ';';
	
	private User user;
	private Double value;
	private Status status;
	private Ad ad;
	
	public Bid(Ad ad) {
		this.ad = ad;
		this.status = Status.PENDING;
	}
	
	public enum Status {
		PENDING, ACCEPTED, REJECTED; 
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Ad getAd() {
		return ad;
	}
	
	public String serialize() {
		StringBuilder out = new StringBuilder();
		out.append("adId=").append(ad.getId()).append(SEPARATOR);
		out.append("status=").append(status);
		return out.toString();
	}

}
