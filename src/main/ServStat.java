package main;
/**
 * Servlet implementation class ServStat
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

//@WebServlet("/ServStat")
public class ServStat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpSalesRecords opsr;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServStat() {
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
		opsr = new OpSalesRecords(conn);
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
		out.println("<h1>SalesRecords List</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");
	    out.println("<tr><td style='width: 10%'>SalesRecordsID</td><td>Date Time</td><td style='width: 10%'>Number</td><td style='width: 10%'>Unit Price</td><td style='width: 15%'>Total Price</td><td style='width: 10%'>Operations</td></tr>");
	      
	    if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
	    	printStatList(out);
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

	public void printStatList(PrintWriter out) {
	    ArrayList salerdList = opsr.getStatList();
//	    ArrayList deptList = se.getObject(new Departments());
	    for(int i = 0; i < salerdList.size(); i++) {
	    	SalesRecords salerd = (SalesRecords) salerdList.get(i);
	    	out.println(salerd.toStatHTML());
	    }
	}
}
