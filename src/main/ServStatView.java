package main;
/**
 * Servlet implementation class ServStatView, details of a specific Sales Records
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

//@WebServlet("/ServStatView")
public class ServStatView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpSalesRecords opsr;
	private OpComponents opcomp;
	private double totalcost;
	private double profit;
	private double totalprice;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServStatView() {
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
		opcomp = new OpComponents(conn);
		totalcost = 0.0;
		totalprice = 0.0;
		profit = 0.0;
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
		SalesRecords srview = opsr.getStatView(param);
		totalprice = srview.getTotalprice();
		int pid = srview.getPid();
		int num = srview.getNum();
		String status = "";
		if(srview.getIsdel() == 0) {
			status = "In List";
		}
		else {
			status = "Deleted";
		}
		out.println("<h1>Dealer Infomation</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");
		out.println("<tr><td style='width: 20%'>SalesRecordsID</td><td style='width: 80%'>" + srview.getSid() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Date</td><td style='width: 80%'>" + srview.getDatetime() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Number</td><td style='width: 80%'>" + srview.getNum() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Price</td><td style='width: 80%'>" + srview.getPrice() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Product</td><td style='width: 80%'>" + srview.getProd() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Total Price</td><td style='width: 80%'>" + srview.getTotalprice() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Each Component Cost</td><td style='width: 80%'>" + printComponents(out, pid, num) + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Total Components Cost</td><td style='width: 80%'>" + totalcost + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Total Profit</td><td style='width: 80%'>" + profit + "</td></tr>");
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

	private String printComponents(PrintWriter out, int pid, int num) {
		totalcost = 0;
		ArrayList compList = opcomp.getPriceList(pid);
		String strComp = ""; 
	    for(int i = 0; i < compList.size(); i++) {
	    	Components comp = (Components) compList.get(i);
	    	strComp += "Component: " + comp.getName() + "<br />Price: " + comp.getPrice() + "</br />";
	    	totalcost += comp.getPrice();
	    }
	    totalcost = totalcost * num;
	    profit = totalprice - totalcost;
	    return strComp;
	}
}
