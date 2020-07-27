package com.hzau.dto;

import java.util.List;

import com.hzau.entity.Dept;

public class OrganDto {
	int organ_id;
	String organ_name;
	List<Dept> deptlist;
	public int getOrgan_id() {
		return organ_id;
	}
	public void setOrgan_id(int organ_id) {
		this.organ_id = organ_id;
	}
	public String getOrgan_name() {
		return organ_name;
	}
	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}
	public List<Dept> getDeptlist() {
		return deptlist;
	}
	public void setDeptlist(List<Dept> deptlist) {
		this.deptlist = deptlist;
	}
}
