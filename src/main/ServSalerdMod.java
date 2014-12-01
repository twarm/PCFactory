package main;
/**
 * Servlet implementation class ServSalerdMod
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


//@WebServlet("/ServSalerdMod")
public class ServSalerdMod extends HttpServlet {
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
    public ServSalerdMod() {
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
		int param = Integer.parseInt(request.getParameter("id"));
		SalesRecords sr = opsr.getSp(param);
		String navimenu = nm.printMenu();
		PrintWriter out = response.getWriter();
		out.println(navimenu);
		out.println("<h1>Modify Sales Records</h1>");
		out.println("<form name='frmSalerdMod' action='/PCFactory/ServSalerdModProcessing?id=" + sr.getSid() + "' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");

		out.println("<tr><td width='40%' align='center'>Number</td><td><input type='text' id='tbNum' name='tbNum' value='" + sr.getNum() + "'></tr>");
		out.println("<tr><td width='40%' align='center'>Price</td><td><input type='text' id='tbPrice' name='tbPrice' value='" + sr.getPrice() + "'></tr>");
		if (!message.equalsIgnoreCase("servus")) {
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} else {
			out.println(getDealerListHtml(sr.getDealerid()));		
			out.println(getProductsListHtml(sr.getPid()));
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
	
	protected String getDealerListHtml(int id) {
		StringBuffer builder = new StringBuffer();
		ArrayList dealerlist = opdeal.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Dealer:</td>");
		builder.append("<td><select id='Dealers' name='Dealers'>");
		for (int j = 0; j < dealerlist.size(); j++) {
			Dealers dealer = (Dealers) dealerlist.get(j);
			if(dealer.getDealerid() == id) {
				builder.append("<option selected value='" + dealer.dealerid + "'>" + dealer.name + "</option>");
			}
			else {
				builder.append("<option value='" + dealer.dealerid + "'>" + dealer.name + "</option>");
			}
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}
	
	protected String getProductsListHtml(int id) {
		StringBuffer builder = new StringBuffer();
		ArrayList prodlist = opprod.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Product:</td>");
		builder.append("<td><select id='Products' name='Products'>");
		for (int j = 0; j < prodlist.size(); j++) {
			Products prod = (Products) prodlist.get(j);
			if(prod.getPid() == id) {
				builder.append("<option selected value='" + prod.pid + "'>" + prod.name + "</option>");
			}
			else {
				builder.append("<option value='" + prod.pid + "'>" + prod.name + "</option>");
			}
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}

}
