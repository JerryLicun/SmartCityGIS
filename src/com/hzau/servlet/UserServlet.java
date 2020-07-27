package com.hzau.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.hzau.entity.Person;
import com.hzau.service.UserService;

@WebServlet("/logserver")
public class UserServlet extends HttpServlet {
	private UserService userService = new UserService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		//获取请求的参数method
		String method = request.getParameter("method");
		if(method.equals("login")){
			login(request,response);
		}else if(method.equals("logout")){
			logout(request,response);
		}
	}

	
	/**登录*/
	private void login(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Person person = userService.queryPersonByUsername(username);
		if(person==null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "用户名不存在");
		}else if(!person.getPassword().equals(password)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "密码错误");
		
		}else{
			//将当前登录的用户存入会话作用域 
			int dept_id =userService.queryDeptid(person.getJobid());
			int organ_id =userService.queryOrganid(dept_id);
			request.getSession().setAttribute("loginPerson", person);
			PrintWriter out = response.getWriter();
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("msg", "登录成功！");
			jsonobj.put("data", person);
			jsonobj.put("dept_id", dept_id);
			jsonobj.put("organ_id", organ_id);
			out.write(JSON.toJSONString(jsonobj));
		}
	}
	
	/**注销退出*/
	private void logout(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		ses.invalidate();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("msg", "登出成功！");
		out.write(JSON.toJSONString(jsonobj));
	}
	
}
