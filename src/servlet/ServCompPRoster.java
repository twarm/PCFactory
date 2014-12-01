package servlet;
/**
 * Servlet implementation class ServCompPRoster, list of Products using a specific Component
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Products;

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
import operation.OpComponents;

//@WebServlet("/ServCompPRoster")
public class ServCompPRoster extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpComponents opcomp;

	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServCompPRoster() {
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
	    out.println(navimenu);
		out.println("<h1>List of Products using this Component</h1>");
	    out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");
	    out.println("<tr><td style='width: 10%'>PID</td><td style='width: 65%'>Name</td><td style='width: 10%'>Stock</td><td style='width: 15%'>Operations</td></tr>");
	      
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
	    	printProdList(out, param);
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
	
	public void printProdList(PrintWriter out, int param) {
	      
	    ArrayList prodList = opcomp.getProdList(param);
//	    ArrayList prodList = se.getObject(new Products());
	    for(int i = 0; i < prodList.size(); i++) {
	    	Products prod = (Products) prodList.get(i);
	    	out.println(prod.toHTML());
	    }
	}
}
