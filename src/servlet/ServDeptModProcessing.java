package servlet;
/**
 * Servlet implementation class ServdeptModProcessing
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Departments;

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
import operation.OpDepartments;


//@WebServlet("/ServdeptModProcessing")
public class ServDeptModProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpDepartments opdept;
//	private SearchEngine se;
	
	private String message;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServDeptModProcessing() {
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
		opdept = new OpDepartments(conn);
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
		Departments dept = new Departments();
		int param = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("tbName");
		double bud = Double.parseDouble(request.getParameter("tbBudget"));
		dept.setName(name);
		dept.setBudget(bud);
		if (!message.equalsIgnoreCase("servus")) {	
			PrintWriter out = response.getWriter();
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} 
		else {
			opdept.modDept(dept, param);
			response.sendRedirect("/PCFactory/ServDeptList");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
