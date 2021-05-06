package model;

import java.util.List;

public class Ad implements Comparable<Ad> {
	
	private Integer id;
	private String name;
	private String text;
	private Integer priority;
	private Double productPrice;
	private Double adPrice;
	private List<String> keywords;
	private AdStatus status;
	private User user;
	
	public Ad() {
		status = AdStatus.PENDING;
		priority = 5;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Double getProductPrice() {
		return productPrice;
	}
	
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	
	public Double getAdPrice() {
		return adPrice;
	}
	
	public void setAdPrice(Double adPrice) {
		this.adPrice = adPrice;
	}
	
	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public AdStatus getStatus() {
		return status;
	}
	
	public void setStatus(AdStatus status) {
		this.status = status;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int compareTo(Ad other) {
		return priority.compareTo(other.priority);
	}

}
