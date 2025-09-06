package com.labgeek.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.labgeek.models.Task;
import com.labgeek.utils.DatabaseConnection;

import persistance.TaskDAO;

public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaskDAO taskDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskServlet() {

		taskDAO = new TaskDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("action")!=null && (request.getParameter("action").toString().equals("new"))) {
			request.setAttribute("contentPage", "addTask.jsp");
			request.getRequestDispatcher("layout.jsp").forward(request, response);

		} else {
			// TODO Auto-generated method stub
			int page = 1;
			int recordsPerPage = 10;
			String search = request.getParameter("search"); // new
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));
			try {
				List<Task> tasks = taskDAO.findAll((page - 1) * recordsPerPage, recordsPerPage, search);
				request.setAttribute("tasks", tasks);
				int noOfRecords = taskDAO.count(search);
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("currentPage", page);
				request.setAttribute("contentPage", "tasks.jsp");
				request.setAttribute("search", search == null ? "" : search);

				request.getRequestDispatcher("layout.jsp").forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		String title=request.getParameter("title").toString();
		String description=request.getParameter("description").toString();
		String status=request.getParameter("status").toString();
		
		Task task=new Task();
		task.setTitle(title);
		task.setDescription(description);
		task.setStatus(status);
		try {
			int result=taskDAO.insert(task);
			if(result==1) {
				session.setAttribute("flashOk","Success your task has been inserted");
				
			}else {
				session.setAttribute("flashErr","Erro your task has not been inserted");

			}
			response.sendRedirect(request.getContextPath()+"/tasks");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
