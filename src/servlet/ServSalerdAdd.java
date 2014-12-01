package servlet;
/**
 * Servlet implementation class ServSalerdAdd
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Dealers;
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
import operation.OpDealers;
import operation.OpProducts;
import operation.OpSalesRecords;

//@WebServlet("/ServSalerdAdd")
public class ServSalerdAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpSalesRecords opsr;
	private OpDealers opdeal;
	private OpProducts opprod;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServSalerdAdd() {
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
		opdeal = new OpDealers(conn);
		opprod = new OpProducts(conn);
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
		out.println("<h1>Create Sales Records</h1>");
		out.println("<form name='frmSalerdAdd' action='/PCFactory/ServSalerdAddProcessing' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");

		out.println("<tr><td width='40%' align='center'>Number</td><td><input type='text' id='tbNum' name='tbNum' value=''></tr>");
		out.println("<tr><td width='40%' align='center'>Price</td><td><input type='text' id='tbPrice' name='tbPrice' value=''></tr>");
		if (!message.equalsIgnoreCase("servus")) {
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} else {
			out.println(getDealerListHtml());		
			out.println(getProductsListHtml());
		}		
		out.println("<tr><td colspan='2' align='center'><input type='submit' name='btnSubmit' value='Confirm'></td></tr>");

		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected String getDealerListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList dealerlist = opdeal.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Dealer:</td>");
		builder.append("<td><select id='Dealers' name='Dealers'>");
		for (int j = 0; j < dealerlist.size(); j++) {
			Dealers dealer = (Dealers) dealerlist.get(j);
			builder.append("<option value='" + dealer.dealerid + "'>" + dealer.name + "</option>");
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}
	
	protected String getProductsListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList prodlist = opprod.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Product:</td>");
		builder.append("<td><select id='Products' name='Products'>");
		for (int j = 0; j < prodlist.size(); j++) {
			Products prod = (Products) prodlist.get(j);
			builder.append("<option value='" + prod.pid + "'>" + prod.name + "</option>");
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}

}
