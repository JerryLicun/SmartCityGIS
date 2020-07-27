package com.hzau.service;

import java.util.List;

import com.hzau.dao.BaseDao;
import com.hzau.dao.UserDao;
import com.hzau.entity.Dept;
import com.hzau.entity.Job;
import com.hzau.entity.Organ;
import com.hzau.entity.Person;
import com.hzau.dto.PersonDto;

public class UserService {
	private UserDao userDao = new UserDao();
	private BaseDao baseDao =new BaseDao();
	public Person queryPersonByUsername(String username) {
		return userDao.querPersonByUsername(username);
	}
//	验证用户名
	public boolean checkUsername(String username){
		return userDao.checkUsername(username);
	}
//	从组织到部门到岗位
	public List<Organ> queryOrganList() {
		
		return baseDao.queryOrganList();
	}
	
	public List<Dept> queryDeptList(int organ_id) {
		
		return baseDao.queryDeptList(organ_id);
	}
	
	public List<Job> queryJobList(int dept_id){
		return baseDao.queryJobList(dept_id);
	}
	public int queryDeptid(int jobid){
		return baseDao.queryDeptByJob(jobid);
	}
	public int queryOrganid(int dept_id){
		return baseDao.queryOrganByDept(dept_id);
	}
	
	
	public boolean addPerson(Person p) {
		return userDao.addPerson(p);
	}

	public boolean deletePersonlist(int []personidlist){
		return userDao.deletePersonlist(personidlist);
	}
	public boolean deletePersonByPersonId(int person_id){
		return userDao.deletePersonByPersonId(person_id);
	}
	public Person queryPersonByPersonId(int personid){
		return userDao.queryPersonByPersonId(personid);
	}
	public boolean modifyPersonByPersonId(Person person , int personid){
		return userDao.modifyPersonByPersonId(person ,personid);
	}
	public List<PersonDto> queryPersonListByUserName(String username,int page,int length){
		return userDao.queryPersonListByUserName(username,page,length);
	}
	public int  queryTotalByUsername(String username){
		return userDao.queryTotalByUsername(username);
	}
	public List<PersonDto> queryPersonListByRealName(String realname ,int page,int length) {
		return userDao.queryPersonListByRealName(realname ,page,length);
	}
	public int  queryTotalByRealname(String realname){
		return userDao.queryTotalByRealname(realname);
	}
	public List<PersonDto> queryPersonListByJobName(String job_name ,int page,int length) {
		return userDao.queryPersonListByJobName(job_name ,page,length) ;
	}
	public int  queryTotalByjobname(String job_name){
		return userDao.queryTotalByjobname(job_name);
	}
	public List<PersonDto> queryPersonDtoList(int page ,int length) {
		return userDao.queryPersonDtoList(page , length) ;
	}
	public int queryTotal() {
		return userDao.queryTotal();
	}
	
}
