package com.crud.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crud.dao.UserDAO;
import com.crud.model.Occupation;
import com.crud.model.User;

public class UserEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String INSERT = "/userAdd.jsp";
	private static String EDIT = "/userUpdate.jsp";
	private static String LIST_USER = "/listUser.jsp";
	private UserDAO dao;

	public UserEditController() {
		super();
		dao = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("delete")) {
			String userId = request.getParameter("rut");
			dao.deleteUser(userId);
			forward = LIST_USER;
			request.setAttribute("users", dao.getAllUsers());
		} else if (action.equalsIgnoreCase("edit")) {
			forward = EDIT;
			System.out.println("UPDATE 1" + request.getParameter("rut"));
			String userId = request.getParameter("rut");
			System.out.println("UPDATE 2" + userId);
			User user = dao.getUserById(userId);
			request.setAttribute("user", user);
		} else if (action.equalsIgnoreCase("listUser")) {
			forward = LIST_USER;
			request.setAttribute("users", dao.getAllUsers());
		} else {
			forward = INSERT;
		}

		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Occupation occupation = new Occupation();
		occupation.setId(Integer.parseInt(request.getParameter("idoccupation")));
		occupation.setName(request.getParameter("occupation"));
		User user = new User(request.getParameter("rut"), request.getParameter("firstName"),
				request.getParameter("secondName"), request.getParameter("lastName"), request.getParameter("surName"),
				request.getParameter("address"), Double.valueOf(request.getParameter("weight")),
				Double.valueOf(request.getParameter("height")), Integer.parseInt(request.getParameter("age")),
				occupation);

		System.out.println("USER EDIT " + user);

		dao.updateUser(user);
		RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
		request.setAttribute("users", dao.getAllUsers());
		view.forward(request, response);
	}
}
