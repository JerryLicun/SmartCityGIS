package com.hzau.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzau.dto.DeptDto;
import com.hzau.dto.OrganDto;
import com.hzau.entity.Dept;
import com.hzau.entity.Organ;
import com.hzau.service.DeptService;

@WebServlet("/api/deptserver")
public class DeptServlet extends HttpServlet {
	private DeptService deptService = new DeptService();
	private  Log logger = LogFactory.getLog(this.getClass());
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//获取请求的参数method
		String method = request.getParameter("method");
		if(method.equals("deptList")){
			deptList(request,response);
		}else if(method.equals("addDept")){
			addDept(request,response);
		}else if(method.equals("getDept")){
			getDept(request,response);
		}else if(method.equals("changeDept")){
			changeDept(request,response);
		}else if(method.equals("delDept")){
			delDept(request,response);
		}else if(method.equals("delDeptlist")){
			delDeptlist(request,response);
		}else if(method.equals("searchListbyOrganName")){
			searchListbyOrganName(request,response);
		}else if(method.equals("searchListbyDeptName")){
			searchListbyDeptName(request,response);
		}
	}
//	分页全部
	private void deptList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<DeptDto> deptlist =deptService.getDeptlist(page, length);
		int total =deptService.deptlistTotal();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", deptlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	添加
	private void addDept(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dept_name = request.getParameter("dept_name");
		dept_name = new String(dept_name.getBytes("8859_1"), "utf8");
		int organ_id = Integer.parseInt(request.getParameter("organ_id"));
		boolean flag =deptService.addDept(organ_id, dept_name);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("添加成功");
			logger.info("添加部门");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "添加失败");
		}
	}
//	查询部门
	private void getDept(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int dept_id = Integer.parseInt(request.getParameter("dept_id"));
		Dept dept =deptService.queryById(dept_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(dept));
	}
//	修改部门
	private void changeDept(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int dept_id = Integer.parseInt(request.getParameter("dept_id"));
		String dept_name = request.getParameter("dept_name");
		dept_name = new String(dept_name.getBytes("8859_1"), "utf8");
		int organ_id = Integer.parseInt(request.getParameter("organ_id"));
		boolean flag =deptService.changeDept(dept_id, organ_id, dept_name);
		
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("修改成功");
			logger.info("修改部门");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "修改失败");
		}
	}
//	删除部门
	private void delDept(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int dept_id = Integer.parseInt(request.getParameter("dept_id"));
		boolean flag =deptService.delDept(dept_id);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("删除部门");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	批量删除
	private void delDeptlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] joblist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			joblist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =deptService.delDeptlist(joblist);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("批量删除部门");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	两模糊查找
	private void searchListbyOrganName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String organ_name=request.getParameter("organ_name");
		organ_name = new String(organ_name.getBytes("8859_1"), "utf8");
		List<DeptDto> organlist =deptService.getDeptlistByorganname(organ_name, page, length);
		int total =deptService.organnameTotal(organ_name);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", organlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	private void searchListbyDeptName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String dept_name=request.getParameter("dept_name");
		dept_name = new String(dept_name.getBytes("8859_1"), "utf8");
		List<DeptDto> organlist =deptService.getDeptlistBydeptname(dept_name, page, length);
		int total =deptService.deptnameTotal(dept_name);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", organlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
}
