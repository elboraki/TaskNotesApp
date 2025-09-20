package com.labgeek.controller;

import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.labgeek.models.Note;
import com.labgeek.models.User;

import persistance.NoteDAO;

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

		if (request.getParameter("action") != null && request.getParameter("action").toString().equals("new")) {
			request.setAttribute("contentPage", "notes/addNote.jsp");
			request.getRequestDispatcher("layout.jsp").forward(request, response);
		}

		else if (request.getParameter("action") != null && request.getParameter("action").toString().equals("edit")) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println("id--->" + id);
				Note currentNote = noteDAO.getById(id);
				System.out.print("id--->" + currentNote.getBody());

				request.setAttribute("note", currentNote);
				request.setAttribute("contentPage", "notes/editNote.jsp");
				request.getRequestDispatcher("layout.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (request.getParameter("action") != null
				&& request.getParameter("action").toString().equals("delete")) {
			try {
				HttpSession session = request.getSession();
				int id = Integer.parseInt(request.getParameter("id"));
				int row = noteDAO.delete(id);
				if (row == 1) {
					session.setAttribute("flashOk", "Success your note has been deleted");

				} else {
					session.setAttribute("flashErr", "Error your note has not been deleted");

				}
				response.sendRedirect(request.getContextPath() + "/notes");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
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
		HttpSession session = request.getSession();
		System.out.println("===========================" + request.getParameter("action") + "======================");
		if (request.getParameter("action") != null && request.getParameter("action").toString().equals("edit")) {
			System.out.println("=====================PUT===================");

			doPut(request, response);
		} else {

			try {
				NoteDAO noteDAO = new NoteDAO();
				Note note = new Note();
				com.labgeek.models.Category category = new com.labgeek.models.Category();
				User user = (User) session.getAttribute("currentUser");
				note.setUser(user);

				note.setBody(request.getParameter("body").toString());
				category.setId(Integer.parseInt(request.getParameter("categorie").toString()));
				note.setCategory(category);
				int row = noteDAO.create(note);
				if (row == 1) {
					session.setAttribute("flashOk", "Success your note has been deleted");

				} else {
					session.setAttribute("flashErr", "Error your note has not been deleted");

				}
				response.sendRedirect(request.getContextPath() + "/notes");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			String body = req.getParameter("body");
			int cateogry_id = Integer.parseInt(req.getParameter("categorie"));

			NoteDAO noteDAO = new NoteDAO();
			Note note = noteDAO.getById(id);
			com.labgeek.models.Category cat = new com.labgeek.models.Category();
			cat.setId(cateogry_id);
			User user = (User) session.getAttribute("currentUser");
			System.out.println("User id-->" + user.getId());
			note.setBody(body);
			note.setCategory(cat);
			note.setUser(user);
			int row = noteDAO.update(note);
			if (row == 1) {
				session.setAttribute("flashOk", "Success your note has been updated");

			} else {
				session.setAttribute("flashErr", "Error your note has not been updated");

			}
			resp.sendRedirect(req.getContextPath() + "/notes");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
