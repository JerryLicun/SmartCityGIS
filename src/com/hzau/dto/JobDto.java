package com.hzau.dto;

public class JobDto {
	private int job_id;
	private String job_name;
	private int dept_id;
	private String dept_name;
	private int organ_id;
	private String organ_name;
	public int getJob_id() {
		return job_id;
	}
	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
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
	
}
