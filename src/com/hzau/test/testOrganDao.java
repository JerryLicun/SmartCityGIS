package com.hzau.test;

import java.util.List;

import com.hzau.dao.*;
import com.hzau.dto.OrganDto;
import com.hzau.entity.*;

public class testOrganDao {
	public static void main(String[] args){
		OrganDao organDao =new OrganDao();
		BaseDao baseDao =new BaseDao();
		
//		分页查询organlist
//		List<OrganDto> organlist=organDao.queryOrganList(2, 5);
//		for(OrganDto organdto : organlist){
//			System.out.println(organdto.getOrgan_id()+"  "+organdto.getOrgan_name()+"  ");
//		}
//		
		
//		分页模糊查找
//		List<Organ> organlist=organDao.queryOrganListByOgname(1, 3, "山区");
//		for(Organ organ : organlist){
//			System.out.println(organ.getOrgan_id()+"  "+organ.getOrgan_name()+"  ");
//		}
		
//		通过organ_id查询单条数据
//		Organ organ=organDao.queryOrganById(5);
//		System.out.println(organ.getOrgan_id()+"  "+organ.getOrgan_name()+"  ");
		
		
//		新增一条数据
//		boolean flag = organDao.addOrgan(7, "洪山区商务部");
//		System.out.println(flag?"成功":"失败");
		
//		更新一条数据
//		boolean flag =organDao.changeOrgan(1,8,"武汉市政府办公厅");
//		System.out.println(flag?"成功":"失败");
//		
//		删除一条数据
//		boolean flag =organDao.delOrgan(7);
//		System.out.println(flag?"成功":"失败");
//		
//		查找所有
//		int total =organDao.OrganlistTotal();
//		System.out.println(total);
//		InformDao infoDao =new InformDao();
//		Inform info =new Inform();
//		info.setHot(false);
//		info.setInfocontext("151614684164684164");
//		info.setInfoname("杨");
//		info.setInfotime("1651631");
//		info.setInfotitle("大大");
//		info.setKeyword("12313");
//		boolean flag = infoDao.addInform(info);
//		if(flag){
//			System.out.println("true!");
//		}
	}
}
