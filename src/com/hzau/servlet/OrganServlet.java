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
import com.hzau.dto.OrganDto;
import com.hzau.entity.Organ;
import com.hzau.service.OrganService;

@WebServlet("/organserver")
public class OrganServlet extends HttpServlet {
	private OrganService organService =new OrganService();
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
		String method=request.getParameter("method");
		if(method.equals("organlist")){
			organList(request,response);
		}else if(method.equals("addorgan")){
			addOrgan(request,response);
		}else if(method.equals("getorgan")){
			getOrgan(request,response);
		}else if(method.equals("changeorgan")){
			changeOrgan(request,response);
		}else if(method.equals("delOrgan")){
			delOrgan(request,response);
		}else if(method.equals("searchorgan")){
			searchListbyName(request,response);
		}else if(method.equals("delOrganlist")){
			delOrganlist(request,response);
		}
	}
//	分页获取列表
	private void organList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<OrganDto> organlist =organService.queryOrganlist(page, length);
		int total =organService.organListTotal();
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", organlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	
//	添加组织
	private void addOrgan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String organ_name = request.getParameter("organ_name");
		organ_name = new String(organ_name.getBytes("8859_1"), "utf8");
		boolean flag =organService.addOrgan(organ_name);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("添加成功");
			logger.info("添加组织");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "添加失败");
		}
	}
//	查询组织
	private void getOrgan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int organ_id = Integer.parseInt(request.getParameter("organ_id"));
		Organ organ =organService.getOrgan(organ_id);
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(organ));
	}
//	修改组织
	private void changeOrgan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int organ_id = Integer.parseInt(request.getParameter("organ_id"));
		String organ_name = request.getParameter("organ_name");
		organ_name = new String(organ_name.getBytes("8859_1"), "utf8");
		boolean flag =organService.changeOrgan(organ_id, organ_name);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("修改成功");
			logger.info("修改组织");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "修改失败");
		}
	}
//	删除组织
	private void delOrgan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int organ_id = Integer.parseInt(request.getParameter("organ_id"));
		boolean flag =organService.delOrgan(organ_id);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("删除组织");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
	private void delOrganlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String arraylist=request.getParameter("list");
		arraylist = new String(arraylist.getBytes("8859_1"), "utf8");
		String[] list = arraylist.split(",");
		int[] organlist = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			organlist[i]=Integer.parseInt(list[i]);
		}
		boolean flag =organService.delOrganlist(organlist);
		PrintWriter out = response.getWriter();
		if(flag){
			out.write("删除成功");
			logger.info("批量删除组织");
		}else{
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "删除失败");
		}
	}
//	模糊查找
	private void searchListbyName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		int length = Integer.parseInt(request.getParameter("length"));
		String organ_name=request.getParameter("organ_name");
		organ_name = new String(organ_name.getBytes("8859_1"), "utf8");
		List<OrganDto> organlist =organService.queryOrganListByOgname(page, length, organ_name);
		int total =organService.OrganlistTotal(organ_name);
		PrintWriter out = response.getWriter();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", organlist);
		jsonobj.put("page", page);
		jsonobj.put("total", total);
		out.write(JSON.toJSONString(jsonobj));
	}
	
	
}
