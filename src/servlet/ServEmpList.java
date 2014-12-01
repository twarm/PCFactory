package servlet;
/**
 * 
 * Servlet of Employees list
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */

import items.Employees;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBSwitch;
import operation.NaviMenu;
import operation.OpEmployees;

//@WebServlet("/ServEmployeesList")
public class ServEmpList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpEmployees opemp;
//	private SearchEngine se;

	private String message;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServEmpList() {
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
		response.setContentType("text/html");
		String auth = (String) request.getSession(true).getAttribute("auth");
		String dept = (String) request.getSession(true).getAttribute("dept");
		String ssn = (String) request.getSession(true).getAttribute("ssn");
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
	    out.println(navimenu);
		out.println("<h1>Employees List</h1>");
		out.println("<h3><a href='ServEmpAdd' style='text-decoration: underline'>Add a new Employee</a><h3>");
	    out.println("<table align='center' border='1' style='border: 1px solid black; width: 70%'>");
	    out.println("<tr><td style='width: 15%'>SSN</td><td style='width: 15%'>Name</td><td style='width: 20%'>Department</td><td style='width: 20%'>Work Since</td><td style='width: 15%'>Title</td><td style='width: 15%'>Operations</td></tr>");
	      
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
	    	printEmpList(out);
	    }
		  
	    out.println("</table>");
	    out.println("</body>");
	    out.println("</html>");
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);  
	}
	
	public void printEmpList(PrintWriter out) {
	      
	    ArrayList empList = opemp.getList();
//	    ArrayList empList = se.getObject(new Employees());
	    for(int i = 0; i < empList.size(); i++) {
	    	Employees emp = (Employees) empList.get(i);
	    	out.println(emp.toHTML());
	    }
	}

}
