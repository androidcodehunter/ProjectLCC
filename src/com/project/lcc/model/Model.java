package com.project.lcc.model;

import java.io.Serializable;

public class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String Mobile;

	private String Image;

	private String Voice;
	private String PaddyTreeAge;

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getVoice() {
		return Voice;
	}

	public void setVoice(String voice) {
		Voice = voice;
	}

	public String getPaddyTreeAge() {
		return PaddyTreeAge;
	}

	public void setPaddyTreeAge(String paddyTreeAge) {
		PaddyTreeAge = paddyTreeAge;
	}

}
