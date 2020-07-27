package com.hzau.service;

import java.util.List;

import com.hzau.dao.JobDao;
import com.hzau.dto.JobDto;
import com.hzau.entity.Job;

public class JobService {
	JobDao jobdao=new JobDao();
	
	 public List<JobDto> queryJobAll(int page, int length){
		return jobdao.queryJobAll(page, length);
	}
	 
	 public int queryJobTotal(){
		 return jobdao.queryJobTotal();
	 }
	 
	 public boolean deletjob(int job_id){
		 return jobdao.deletjob(job_id);
	 }
	 
	 public boolean deletjobList(int[]job_id){
		 return jobdao.deletjobList(job_id);
	 }
	 
	 public boolean addJob_id(int dept_id,String job_name){
		 return jobdao.addJob_id(dept_id, job_name);
	 }
	 
	 public Job queryJob_Id(int job_id){
		 return jobdao.queryJob_Id(job_id);
	 }
	 
	 public boolean upJob(int job_id,int dept_id,String job_name){
		 return jobdao.upJob(job_id, dept_id, job_name);
	 }
	 
	 public List<JobDto>queryJobListByJobName(String jobname,int page,int length){
		 return jobdao.queryJobListByJobName(jobname, page, length);
	 }
	 
	 public int queryJobNameTotal(String job_name){
		 return jobdao.queryJobNameTotal(job_name);
	 }
	 
	 public List<JobDto>queryJobListByDeptName(String dept_name,int page,int length){
		 return jobdao.queryJobListByDeptName(dept_name, page, length);
	 }
	 
	public int queryDeptNameTotal(String dept_name){
		return jobdao.queryDeptNameTotal(dept_name);
	}
}
