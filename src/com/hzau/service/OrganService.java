package com.hzau.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hzau.dao.BaseDao;
import com.hzau.dao.OrganDao;
import com.hzau.dto.OrganDto;
import com.hzau.entity.Dept;
import com.hzau.entity.Organ;

public class OrganService {
	private BaseDao baseDao=new BaseDao();
	private OrganDao organDao=new OrganDao();
	
	
	public List<OrganDto> queryOrganlist (int page,int length){
		return organDao.queryOrganList(page, length);
	}
	public int organListTotal(){
		return organDao.OrganlistTotal();
	}
	public List<Dept> searchDeptByOrgan (int organ_id){
		return baseDao.queryDeptList(organ_id);
	}
	public Organ getOrgan(int organ_id){
		return organDao.queryOrganById(organ_id);
	}
	public boolean addOrgan(String organ_name){
		return organDao.addOrgan(organ_name);
	}
	public boolean changeOrgan(int organ_id,String organ_name){
		return organDao.changeOrgan(organ_id, organ_name);
	}
	public boolean delOrgan(int organ_id){
		return organDao.delOrgan(organ_id);
	}
	public boolean delOrganlist(int[] idlist){
		return organDao.delOrganlist(idlist);
	}
	public List<OrganDto> queryOrganListByOgname(int page,int length,String organ_name){
		return organDao.queryOrganListByOgname(page, length, organ_name);
	}
	public int OrganlistTotal(String organ_name){
		return organDao.searchlistTotal(organ_name);
	}
}
