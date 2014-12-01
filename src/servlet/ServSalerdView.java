package servlet;
/**
 * Servlet implementation class ServSalerdView, information of a specific Sales Record
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.SalesRecords;

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
import operation.NaviMenu;
import operation.OpSalesRecords;

//@WebServlet("/ServSalerdView")
public class ServSalerdView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpSalesRecords opsr;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServSalerdView() {
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
		int param = Integer.parseInt(request.getParameter("id"));
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
		out.println(navimenu);
		SalesRecords srview = opsr.getView(param);
		String status = "";
		if(srview.getIsdel() == 0) {
			status = "In List";
		}
		else {
			status = "Deleted";
		}
		out.println("<h1>Dealer Infomation</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		out.println("<tr><td style='width: 20%'>SalesRecordsID</td><td style='width: 80%'>" + srview.getSid() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Date</td><td style='width: 80%'>" + srview.getDatetime() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Number</td><td style='width: 80%'>" + srview.getNum() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Price</td><td style='width: 80%'>" + srview.getPrice() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Product</td><td style='width: 80%'>" + srview.getProd() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Dealer</td><td style='width: 80%'>" + srview.getDealer() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Status</td><td style='width: 80%'>" + status + "</td></tr>");
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

}
