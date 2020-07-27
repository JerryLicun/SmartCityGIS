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
import com.hzau.dto.JobDto;
import com.hzau.dto.PersonDto;
import com.hzau.entity.Job;
import com.hzau.entity.Person;
import com.hzau.service.JobService;
import com.hzau.service.UserService;

@WebServlet("/api/jobserver")
public class JobServlet extends HttpServlet {

	private JobService jobService = new JobService();
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
		if(method.equals("jobList")){
			jobList(request,response);
		}else if(method.equals("queryById")){
			queryById(request,response);
		}else if(method.equals("changeJob")){
			changeJob(request,response);
		}else if(method.equals("addJob")){
			addJob(request,response);
		}else if(method.equals("searchByjobname")){
			searchByjobname(request,response);
		}else if(method.equals("searchBydeptname")){
			searchBydeptname(request,response);
		}else if(method.equals("delJob")){
			delJob(request,response);
		}else if(method.equals("delJoblist")){
			delJoblist(request,response);
		}
	}
	
//	分页返回所有
	private void jobList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<JobDto> joblist=jobService.queryJobAll(page, length);
		int total = jobService.queryJobTotal();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", joblist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
//	修改操作
	private void queryById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int job_id =Integer.parseInt(request.getParameter("jobid"));
		Job job=jobService.queryJob_Id(job_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(job));
	}
//	修改及新增
	private void addJob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int dept_id =Integer.parseInt(request.getParameter("deptid"));
		String job_name =request.getParameter("jobname");
		job_name = new String(job_name.getBytes("8859_1"), "utf8");
		
		boolean flag=jobService.addJob_id(dept_id, job_name);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("添加成功");
			logger.info("添加岗位");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "添加失败");
		}
	}
	private void changeJob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int job_id =Integer.parseInt(request.getParameter("jobid"));
		int dept_id =Integer.parseInt(request.getParameter("deptid"));
		String job_name =request.getParameter("jobname");
		job_name = new String(job_name.getBytes("8859_1"), "utf8");
		
		boolean flag=jobService.upJob(job_id, dept_id, job_name);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("修改成功");
			logger.info("修改岗位");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "修改失败");
		}
	}
//	模糊查找
	private void searchByjobname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String jobname=request.getParameter("jobname");
		jobname = new String(jobname.getBytes("8859_1"), "utf8");
		List<JobDto> joblist=jobService.queryJobListByJobName(jobname, page, length);
		int total =jobService.queryJobNameTotal(jobname);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", joblist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
		
	}
	
	private void searchBydeptname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String deptname=request.getParameter("deptname");
		deptname = new String(deptname.getBytes("8859_1"), "utf8");
		List<JobDto> joblist=jobService.queryJobListByDeptName(deptname, page, length);
		int total =jobService.queryDeptNameTotal(deptname);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", joblist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
		
	}
//	删除和模糊删除
	private void delJob(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int job_id = Integer.parseInt(request.getParameter("jobid"));
		boolean flag =jobService.deletjob(job_id);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("删除岗位");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
	
	private void delJoblist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] joblist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			joblist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =jobService.deletjobList(joblist);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("批量删除岗位");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
}
