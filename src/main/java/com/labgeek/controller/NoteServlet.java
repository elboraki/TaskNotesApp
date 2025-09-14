package com.labgeek.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.labgeek.models.Task;

import persistance.NoteDAO;
import persistance.TaskDAO;

import java.util.List;
import com.labgeek.models.Note;

/**
 * Servlet implementation class NoteServlet
 */
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoteDAO noteDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoteServlet() {
		noteDAO = new NoteDAO();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(request.getParameter("action")!=null && request.getParameter("action").toString().equals("new")) {
			request.setAttribute("contentPage", "notes/addNote.jsp");
			System.out.println("");
			request.getRequestDispatcher("layout.jsp").forward(request, response);
		}else {
			// TODO Auto-generated method stub
			int page = 1;
			int recordsPerPage = 10;
			String search = request.getParameter("search"); // new
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			try {
				List<Note> notes = noteDAO.getAll((page - 1) * recordsPerPage, recordsPerPage, search);
				request.setAttribute("notes", notes);
				int noOfRecords = noteDAO.count(search);
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
				request.setAttribute("noOfPages", noOfPages);
				request.setAttribute("currentPage", page);
				request.setAttribute("contentPage", "notes/notes.jsp");
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
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
