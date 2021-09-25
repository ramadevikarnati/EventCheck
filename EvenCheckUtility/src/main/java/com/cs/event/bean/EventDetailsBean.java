package com.cs.event.bean;

public class EventDetailsBean {
	private String id;
	private String state;
	private String type;
	private String host;
	private long timestamp;
	private long evenDuration;
	private boolean alert;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getEvenDuration() {
		return evenDuration;
	}
	public void setEvenDuration(long evenDuration) {
		this.evenDuration = evenDuration;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	
}
