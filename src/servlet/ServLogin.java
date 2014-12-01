package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBSwitch;
import operation.OpDepartments;

/**
 * Servlet implementation class ServLogin
 */
//@WebServlet("/ServLogin")
public class ServLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private DBSwitch dbswitch;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dbswitch = new DBSwitch();
		dbswitch.openDBConnection("","","","",0);
		conn = dbswitch.get_conn();
		new OpDepartments(conn);
		String username = request.getParameter("tbAccount");
        String password = request.getParameter("tbKey");
        response.getWriter();
        Statement st = null;
		try {
			String query = "select * from Employees where name = '" + username + "' and pw = '" + password + "' and isdel = 0";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				String ssn = rs.getString("ssn");
				request.getSession(true).setAttribute("user", username);
				request.getSession(true).setAttribute("ssn", ssn);
				int aid = rs.getInt("aid");
				int did = rs.getInt("did");
				String subquery = "select name from Departments where did = " + did + " and isdel = 0";
				ResultSet rsd = st.executeQuery(subquery);
				if(rsd.next()) {
					String dept = rsd.getString("name");
					request.getSession(true).setAttribute("dept", dept);
				}
				subquery = "select name from Authorizations where aid = " + aid + " and isdel = 0";
				ResultSet rsa = st.executeQuery(subquery);
				if(rsa.next()) {
					String auth = rsa.getString("name");
					request.getSession(true).setAttribute("auth", auth);
				}
				rsd.close();
				rsa.close();
				rs.close();
				st.close();
				request.getRequestDispatcher("/ServIndex").forward(request, response);
			}
			else {
				rs.close();
				st.close();
				request.getRequestDispatcher("/error.html").forward(request, response);
			}
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	public void destroy() {
		dbswitch.closeDBConnection();
	}
}
