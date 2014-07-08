package com.classcoordinator.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.coordinator.Assignment;
import com.coordinator.Project;

/**
 * Servlet implementation class FindProjectServlet
 */
public class FindProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/JavaTunesDataSource")
	DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
			
		boolean logIn = false;
		boolean IDExist = false;
		String stringID = request.getParameter("ProjID");
		String pass= request.getParameter("ProjPassword");
		int projectID = Integer.parseInt(stringID);
		int id;
		String userID = request.getParameter("userID");

		Connection conn = null;

		try {
			//conn = ds.getConnection();
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			conn = DriverManager.getConnection(
					"jdbc:derby://localhost:1527/JavaTunesDB", "guest",
					"password");
			Statement stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("Select ID, Name, Password FROM Project");
			
			String password;
			//print the info from the result set		
			while (resultSet.next()){
				
				id = resultSet.getInt("ID");
				password = resultSet.getString("Password");
				
				if(id == projectID)
				{
					if(pass.equals(password))
					{
						System.out.println("id matches");
						request.setAttribute("ProjectID", resultSet.getInt("ID"));
						request.setAttribute("ProjectName", resultSet.getString("Name"));
						request.setAttribute("UserID", userID);
						logIn = true;
						IDExist = true;
						
						
						//get the assignments for this project
						ResultSet AssignmentList = stmt.executeQuery("SELECT * FROM Assignments WHERE Pid = " + projectID);
						//List<Assignment>
						List<Assignment> AList = new LinkedList<Assignment>();
						while (AssignmentList.next()){
							//get the right query value
							Assignment a = new Assignment(AssignmentList.getInt("Aid"), AssignmentList.getInt("Pid"), AssignmentList.getString("Title"), 
									AssignmentList.getString("Task"), AssignmentList.getInt("UserID") );
							//add to the list
							AList.add(a);												
							
						}
												
						
						request.setAttribute("assignmentList", AList);																		
						
						break;
					}
					else
					{
						System.out.println("wrong password");
						request.setAttribute("Message", "Wrong Password, try again");
						logIn = false;
						IDExist = true; //we do this to indicate that project does exist but the password is wrong
						break;
					}
					
				}

			}
			if(!IDExist)
			{
				System.out.println("no project");
				request.setAttribute("Message", "Project Doesn't exist");
				logIn = false;
			}	
				
			
			request.setAttribute("loginResult", logIn);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/projectResults.jsp");
			rd.forward(request, response);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {conn.close();}
			catch (SQLException sqle) {}}
		
	}
	
	
	

}
