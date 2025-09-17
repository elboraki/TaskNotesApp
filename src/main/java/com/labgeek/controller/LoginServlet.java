package com.labgeek.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.labgeek.models.User;

import persistance.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("contentPage", "auth/login.jsp");
		request.getRequestDispatcher("layout.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserDAO userDao = new UserDAO();
		try {
			User currentUser = userDao.authenticate(username, password);
			if (currentUser != null) {

				session.setAttribute("flashOk", "Success authentification !");

				session.setAttribute("username", currentUser.getLogin());
				session.setAttribute("userId", currentUser.getId());
				session.setAttribute("currentUser", currentUser);

				response.sendRedirect(request.getContextPath() + "/tasks");
			} else {
				session.setAttribute("flashErr", "Login or password is incorrect");
				//response.sendRedirect(request.getContextPath() + "/login");
				doGet(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
