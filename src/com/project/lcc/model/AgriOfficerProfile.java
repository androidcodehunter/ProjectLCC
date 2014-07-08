package com.project.lcc.model;

public class AgriOfficerProfile {
	private String title;
	private String profile;
	private String mobile;
	private String agriOffice;

	public AgriOfficerProfile(String title, String profile, String mobile,
			String agriOffice) {
		this.title = title;
		this.profile = profile;
		this.mobile = mobile;
		this.agriOffice = agriOffice;
	}

	public String getAgriOffice() {
		return agriOffice;
	}

	public void setAgriOffice(String agriOffice) {
		this.agriOffice = agriOffice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
