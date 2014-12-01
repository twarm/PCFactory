package main;
/**
 * Servlet implementation class ServCompList, list of components
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

//@WebServlet("/ServCompList")
public class ServCompList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpComponents opcomp;
//	private SearchEngine se;

	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServCompList() {
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
		opcomp = new OpComponents(conn);
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
		out.println("<h1>Components List</h1>");
		out.println("<h3><a href='ServCompAdd' style='text-decoration: underline'>Add a new Component</a><h3>");
	    out.println("<table align='center' border='1' style='border: 1px solid black; width: 85%'>");
	    out.println("<tr><td style='width: 5%'>CID</td><td style='width: 55%'>Name</td><td style='width: 10%'>Stock</td><td style='width: 10%'>Price</td><td style='width: 10%'>Category</td><td style='width: 10%'>Operations</td></tr>");
	      
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
	    	printCompList(out);
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

	public void printCompList(PrintWriter out) {
	      
	    ArrayList compList = opcomp.getList();
//	    ArrayList prodList = se.getObject(new Products());
	    for(int i = 0; i < compList.size(); i++) {
	    	Components comp = (Components) compList.get(i);
	    	out.println(comp.toHTML());
	    }
	}
}
