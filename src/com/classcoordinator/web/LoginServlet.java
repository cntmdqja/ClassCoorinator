package com.classcoordinator.web;
import com.coordinator.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/JavaTunesDataSource")
	DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("UserName");
		String pass= request.getParameter("Password");
		
		Connection conn = null;
//		try {
//			conn = ds.getConnection();

		try {
			//conn = ds.getConnection();
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			conn = DriverManager.getConnection(
					"jdbc:derby://localhost:1527/JavaTunesDB", "guest",
					"password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User user = new User();
		request.setAttribute("User", user);
		//request.setAttribute("UserID", user.getUserID());
		//request.setAttribute("UserName", user.getUserName());
		
		try {
			if (user.logIn(userName, pass))
			{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/LoginResult.jsp");
				rd.forward(request, response);
			}
			else 
			{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/LogInWrong.jsp");
				rd.forward(request, response);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
