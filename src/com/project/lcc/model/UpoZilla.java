package com.project.lcc.model;

public class UpoZilla {

	public int id;
	public String name;
	public int zillId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getZillId() {
		return zillId;
	}

	public void setZillId(int zillId) {
		this.zillId = zillId;
	}

	@Override
	public String toString() {
		return name;
	}
}
