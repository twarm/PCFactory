package servlet;
/**
 * 
 * An implementation of servlet for Dealers list.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */

import items.Dealers;

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
import operation.OpDealers;

//@WebServlet("/ServDealerList")
public class ServDealerList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpDealers opdeal;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServDealerList() {
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
		opdeal = new OpDealers(conn);
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
	    //out.println("<html><head><link href='css/style.css' rel='stylesheet' type='text/css' /></head>");
	    out.println(navimenu);
		out.println("<h1>Dealers List</h1>");
		out.println("<h3><a href='ServDealerAdd' style='text-decoration: underline'>Add a new Dealer</a><h3>");
	    out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
	    out.println("<tr><td style='width: 10%'>DealerID</td><td style='width: 40%'>Name</td><td style='width: 30%'>Phone Number</td><td style='width: 20%'>Operations</td></tr>");
	      
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
		    printDealerList(out);
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
	
	public void printDealerList(PrintWriter out) {
	    ArrayList dealerList = opdeal.getList();
//	    ArrayList deptList = se.getObject(new Departments());
	    for(int i = 0; i < dealerList.size(); i++) {
	    	Dealers dealer = (Dealers) dealerList.get(i);
	    	out.println(dealer.toHTML());
	    }
	}

}
