package main;
/**
 * Servlet implementation class ServAuthRoster, list of Employees with specific title
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

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

//@WebServlet("/ServAuthRoster")
public class ServAuthRoster extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpAuthorizations opauth;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServAuthRoster() {
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
		response.setContentType("text/html");
		String auth = (String) request.getSession(true).getAttribute("auth");
		String dept = (String) request.getSession(true).getAttribute("dept");
		String ssn = (String) request.getSession(true).getAttribute("ssn");
		int param = Integer.parseInt(request.getParameter("id"));
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
	    //out.println("<html><head><link href='css/style.css' rel='stylesheet' type='text/css' /></head>");
	    out.println(navimenu);
		out.println("<h1>List of Employees with the same Title</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		out.println("<tr><td>SSN</td><td>Name</td><td>Work Since</td><td>Title</td><td>Operations</td></tr>");
		if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
		    printEmpList(out, param);
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
	
	public void printEmpList(PrintWriter out, int did) {
	    ArrayList empList = opauth.getEmpList(did);
	    for(int i = 0; i < empList.size(); i++) {
	    	Employees emp = (Employees) empList.get(i);
	    	out.println(emp.toHTMLRoster());
	    }
	}

}
