package main;
/**
 * Servlet implementation class ServAuthList
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

//@WebServlet("/ServAuthList")
public class ServAuthList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpAuthorizations opauth;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServAuthList() {
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
		out.println("<h1>Job Titles List</h1>");
		out.println("<h3><a href='ServAuthAdd' style='text-decoration: underline'>Add a new Title</a><h3>");
	    out.println("<table align='center' border='1' style='border: 1px solid black; width: 40%'>");
	    out.println("<tr><td style='width: 70%'>Name</td><td style='width: 30%'>Operations</td></tr>");
	      
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
	    	printAuthList(out);
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

	public void printAuthList(PrintWriter out) {
	      
//	    ArrayList authList = se.getObject(new Authorizations());
		ArrayList authList = opauth.getList();
	    for(int i = 0; i < authList.size(); i++) {
	    	Authorizations auth = (Authorizations) authList.get(i);
	    	out.println(auth.toHTML());
	    }
	}
}
