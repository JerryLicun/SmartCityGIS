package com.hzau.dto;

import java.util.List;

import com.hzau.entity.Job;

public class DeptDto {
	private int dept_id;
	private String dept_name;
	private String organ_name;
	private List<Job> joblist;
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getOrgan_name() {
		return organ_name;
	}
	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}
	public List<Job> getJoblist() {
		return joblist;
	}
	public void setJoblist(List<Job> joblist) {
		this.joblist = joblist;
	}
	
	
}
