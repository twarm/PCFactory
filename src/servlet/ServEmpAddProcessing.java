package servlet;
/**
 * Servlet implementation class ServEmpAddProcessing
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Employees;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBSwitch;
import operation.OpEmployees;


//@WebServlet("/ServEmpAddProcessing")
public class ServEmpAddProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpEmployees opemp;
//	private SearchEngine se;

	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServEmpAddProcessing() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 
		dbswitch = new DBSwitch();
		message = dbswitch.openDBConnection( 
										  );
		conn = dbswitch.get_conn();
		opemp = new OpEmployees(conn);
//		se = new SearchEngine(conn);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		dbswitch.closeDBConnection();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ssn = request.getParameter("tbSSN");
		String name = request.getParameter("tbName");
		String pw = request.getParameter("tbPW");
		int did = Integer.parseInt(request.getParameter("Departments"));
		int aid = Integer.parseInt(request.getParameter("Auth"));
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		Employees emp = new Employees();
		emp.setSsn(ssn);
		emp.setName(name);
		emp.setPw(pw);
		emp.setAid(aid);
		emp.setDid(did);
		emp.setSince(now);
		emp.setIsdel(0);
		if (!message.equalsIgnoreCase("servus")) {	
			PrintWriter out = response.getWriter();
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} 
		else {
			opemp.addEmp(emp);
			response.sendRedirect("/PCFactory/ServEmpList");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
