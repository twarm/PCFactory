package servlet;
/**
 * Servlet implementation class ServEmpModProcessing
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
import operation.OpAuthorizations;
import operation.OpDepartments;
import operation.OpEmployees;


//@WebServlet("/ServEmpModProcessing")
public class ServEmpModProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpEmployees opemp;
	private OpDepartments opdept;
	private OpAuthorizations opauth;
//	private SearchEngine se;

	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServEmpModProcessing() {
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
		opdept = new OpDepartments(conn);
		opauth = new OpAuthorizations(conn);
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
		String[] test = request.getParameterValues("cbManager");
		int i = 0;
		try {
			if(test.length > 0) {
				i = 1;
			}
		}
		catch (Exception e) {
			i = 0;
		}
		String param = request.getParameter("id");
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
			opemp.modEmp(emp, param);
			if(i == 1) {
				opdept.uptDeptManager(ssn, did);
				opdept.modDeptManager(ssn, did);
			}
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
