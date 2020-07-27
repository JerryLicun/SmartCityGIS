package com.hzau.servlet;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzau.dto.OrganDto;
import com.hzau.dto.PersonDto;
import com.hzau.entity.Dept;
import com.hzau.entity.Job;
import com.hzau.entity.Organ;
import com.hzau.entity.Person;
import com.hzau.service.UserService;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hzau.service.UserService;

@WebServlet("/api/personserver")
public class AdminPersonServlet extends HttpServlet {
	private UserService userService = new UserService();
	private  Log logger = LogFactory.getLog(this.getClass());
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String method=request.getParameter("method");
		if(method.equals("personList")){
			personList(request,response);
		}else if(method.equals("queryById")){
			queryById(request,response);
		}else if(method.equals("queryOrganlist")){
			queryOrganlist(request,response);
		}else if(method.equals("queryDeptlist")){
			queryDeptlist(request,response);
		}else if(method.equals("queryJoblist")){
			queryJoblist(request,response);
		}else if(method.equals("queryOrganid")){
			queryOrganid(request,response);
		}else if(method.equals("queryDeptid")){
			queryDeptid(request,response);
		}else if(method.equals("changePerson")){
			changePerson(request,response);
		}else if(method.equals("addPerson")){
			addPerson(request,response);
		}else if(method.equals("searchByname")){
			searchByname(request,response);
		}else if(method.equals("searchByusername")){
			searchByusername(request,response);
		}else if(method.equals("searchByjob")){
			searchByjob(request,response);
		}else if(method.equals("delPerson")){
			delPerson(request,response);
		}else if(method.equals("delPersonArray")){
			delPersonArray(request,response);
		}else if(method.equals("checkUsername")){
			checkUsername(request,response);
		}
	}

	/**人员列表*/
	private void personList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<PersonDto> personlist=userService.queryPersonDtoList(page, length);
		int total = userService.queryTotal();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", personlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	以下全为edit时的操作
	private void queryById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int personid =Integer.parseInt(request.getParameter("personid"));
		Person person=userService.queryPersonByPersonId(personid);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(person));
	}
//	向下查list
	private void queryOrganlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Organ> organlist = userService.queryOrganList();
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(organlist));
	}
	private void queryDeptlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int organ_id =Integer.parseInt(request.getParameter("organ_id"));
		List<Dept> Deptlist = userService.queryDeptList(organ_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(Deptlist));
	}
	private void queryJoblist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int dept_id =Integer.parseInt(request.getParameter("dept_id"));
		List<Job> Joblist = userService.queryJobList(dept_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(Joblist));
	}
//	向上查id
	private void queryOrganid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int dept_id =Integer.parseInt(request.getParameter("dept_id"));
		int organid = userService.queryOrganid(dept_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(organid));
	}
	private void queryDeptid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int jobid =Integer.parseInt(request.getParameter("jobid"));
		int deptid = userService.queryDeptid(jobid);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(deptid));
	}
//	修改及新增
	private void changePerson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int personid =Integer.parseInt(request.getParameter("personid"));
		String personstr =request.getParameter("person");
		personstr = new String(personstr.getBytes("8859_1"), "utf8");
		Person person =JSON.parseObject(personstr,Person.class);
		boolean flag=userService.modifyPersonByPersonId(person, personid);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("修改成功");
			logger.info("修改用户");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "修改失败");
		}
	}
	private void addPerson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String personstr =request.getParameter("person");
		personstr = new String(personstr.getBytes("8859_1"), "utf8");
		Person person =JSON.parseObject(personstr,Person.class);
		boolean flag =userService.addPerson(person);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("添加成功");
			logger.info("新增用户");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "添加失败");
		}
	}
//	模糊查找
	private void searchByname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String realname=request.getParameter("realname");
		realname = new String(realname.getBytes("8859_1"), "utf8");
		List<PersonDto> personlist=userService.queryPersonListByRealName(realname, page, length);
		int total = userService.queryTotalByRealname(realname);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", personlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	private void searchByusername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String username=request.getParameter("username");
		username = new String(username.getBytes("8859_1"), "utf8");
		List<PersonDto> personlist=userService.queryPersonListByUserName(username, page, length);
		int total = userService.queryTotalByUsername(username);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", personlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	private void searchByjob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String jobname=request.getParameter("jobname");
		jobname = new String(jobname.getBytes("8859_1"), "utf8");
		List<PersonDto> personlist=userService.queryPersonListByJobName(jobname, page, length);
		int total = userService.queryTotalByjobname(jobname);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", personlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	删除
	private void delPerson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int personid = Integer.parseInt(request.getParameter("personid"));
		boolean flag =userService.deletePersonByPersonId(personid);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("删除用户");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	批量删除
	private void delPersonArray(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] personlist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			personlist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =userService.deletePersonlist(personlist);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("批量删除用户");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	检查用户名
	private void checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		username = new String(username.getBytes("8859_1"), "utf8");
		boolean flag =userService.checkUsername(username);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(flag));
	}
}
