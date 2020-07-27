package com.hzau.test;

import java.util.List;

import com.hzau.dao.*;
import com.hzau.dto.DeptDto;
import com.hzau.dto.JobDto;
import com.hzau.dto.PersonDto;
import com.hzau.entity.*;

public class test {
	public static void main(String[] args){
		JobDao jobDao =new JobDao();
		BaseDao baseDao =new BaseDao();
//		添加job_info 测试数据
//		int j =1;
//		for(int i = 46 ;i<61;i++){
//			boolean flag =jobDao.addJob(i, j, "经理");
//			System.out.println(flag?"成功":"失败");
//			j++;
//		}
		
//		通过job_id查dept_id
//		int dept_id =baseDao.queryDeptByJob(55);
//		System.out.println(dept_id);
		
//		通过dept_id查organ_id
//		int organ_id = baseDao.queryOrganByDept(15);
//		System.out.println(organ_id);
		
//		通过organ_id返回deptlist
//		List<Dept>  deptlist= baseDao.queryDeptList(3);
//		for (Dept dept : deptlist){
//			System.out.print(dept.getDept_id()+"  "+dept.getDept_name() +"  ");
//		}
		
//		通过dept_id查joblist
//		List<Job> joblist =baseDao.queryJobList(10);
//		for(Job job : joblist){
//			System.out.println(job.getJob_id()+"  "+job.getJob_name()+"  ");
//		}
		
//		直接返回organlist
//		List<Organ> organlist=baseDao.queryOrganList();
//		for(Organ organ : organlist){
//			System.out.println(organ.getOrgan_id()+"  "+organ.getOrgan_name()+"  ");
//		}
//		OrganDao organdao =new OrganDao();
//		UserDao userdao =new UserDao();
//		boolean flag =organdao.checkOrganname("洪山区税务局");
//		System.out.println(flag);
//		int total =userdao.queryTotal();
////		System.out.println(total);
//		List<PersonDto> personlist =userdao.queryPersonListByJobName("山区", 1, 1);
//		for(PersonDto p : personlist){
//			System.out.println(p.getRelname() +"  "+p.getPersonid()+"  ");
//		}
		JobDao jobdao =new JobDao();
//		List<JobDto> list =jobdao.queryJobListByDeptName("组织部", 1, 10);
//		for(JobDto p : list){
//			System.out.println(p.getDept_name() +"  "+p.getJob_name()+"  ");
//		}
		DeptDao deptdao = new DeptDao();
//		List<DeptDto> deptlist = deptdao.queryDeptListByDeptName("财务部", 1, 20);
//		for(DeptDto p : deptlist){
//			System.out.println(p.getDept_name() +"  "+p.getOrgan_name()+"  ");
//		}
//		int total =deptdao.queryOrganNametotal("武汉");
//		System.out.println(total);
	}
	
}
