package com.hzau.service;
import java.util.List;

import com.hzau.dao.*;
import com.hzau.dto.*;
import com.hzau.entity.*;
public class DeptService {
	DeptDao deptDao = new DeptDao();
//	加和总
	public boolean addDept(int organ_id,String dept_name){
		return deptDao.addDept(organ_id, dept_name);
	}
	public List<DeptDto> getDeptlist(int page,int length){
		return deptDao.getDeptList(page, length);
	}
	public int deptlistTotal(){
		return deptDao.DeptListTotal();
	}
	
//	修改
	public Dept queryById(int dept_id){
		return deptDao.queryDeptList(dept_id);
	}
	public boolean changeDept(int dept_id,int organ_id,String dept_name){
		return deptDao.updataDept(dept_id, organ_id, dept_name);
	}
	public boolean delDept(int dept_id){
		return deptDao.deletDept(dept_id);
	}
	public boolean delDeptlist(int[] deptlist){
		return deptDao.deletDeptlist(deptlist);
	}
	
//	模糊
//	部门名
	public List<DeptDto> getDeptlistBydeptname(String dept_name,int page,int length){
		return deptDao.queryDeptListByDeptName(dept_name, page, length);
	}
	public int deptnameTotal(String dept_name){
		return deptDao.queryBydeptnameTotal(dept_name);
	}
//	组织名
	public List<DeptDto> getDeptlistByorganname(String organ_name,int page,int length){
		return deptDao.queryDeptListByOrganName(organ_name, page, length);
	}
	public int organnameTotal(String organ_name){
		return deptDao.queryOrganNametotal(organ_name);
	}
}
