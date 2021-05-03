package model;

import java.util.Date;
import java.util.List;

public class Ad {
	
	private Integer id;
	private String name;
	private String target;
	private String text;
	private Integer priority;
	private List<String> keywords;
	private List<Date> schedule;
	private User user;

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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<Date> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Date> schedule) {
		this.schedule = schedule;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Ad [id=" + id + ", name=" + name + ", target=" + target + ", text=" + text + ", priority=" + priority
				+ ", keywords=" + keywords + ", schedule=" + schedule + "]";
	}
	
}
