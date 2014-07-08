package com.project.lcc.model;

public class AgriOffice {
	private float latitude;
	private float longitute;
	private String agriOfficeName;
	private String location;
	private String open;
	private String time;
	private String mobileNumber;

	public AgriOffice(String agriOfficeName, String location, String open,
			String time, String mobileNumber, float latitude, float longitute) {
		this.agriOfficeName = agriOfficeName;
		this.location = location;
		this.open = open;
		this.time = time;
		this.mobileNumber = mobileNumber;
		this.latitude = latitude;
		this.longitute = longitute;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitute() {
		return longitute;
	}

	public void setLongitute(float longitute) {
		this.longitute = longitute;
	}

	public String getAgriOfficeName() {
		return agriOfficeName;
	}

	public void setAgriOfficeName(String agriOfficeName) {
		this.agriOfficeName = agriOfficeName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
