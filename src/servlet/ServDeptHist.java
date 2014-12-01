package servlet;
/**
 * Servlet implementation class ServDeptHist, Manager history of a specific Department
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.DepartManager;

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
import dbconnection.SearchEngine;
import operation.NaviMenu;
import operation.OpDepartments;

//@WebServlet("/ServDeptHist")
public class ServDeptHist extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpDepartments opdept;
	private SearchEngine se;
	
	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServDeptHist() {
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
		se = new SearchEngine(conn);
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
		int param = Integer.parseInt(request.getParameter("id"));
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
	    out.println(navimenu);
	    out.println("<h1>Manager History of a Department</h1>");
	    out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
	    out.println("<tr><td>Date Since</td><td>Date To</td><td>SSN</td><td>Employee Name</td></tr>");
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
		    printHistList(out, param);
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

	public void printHistList(PrintWriter out, int did) {
	    ArrayList histList = opdept.getHist(did);
	    for(int i = 0; i < histList.size(); i++) {
	    	DepartManager hist = (DepartManager) histList.get(i);
	    	out.println(hist.toHTML());
	    }
	}
}
