package servlet;
/**
 * Servlet implementation class ServDealerMod
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Dealers;

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
import operation.OpDealers;


//@WebServlet("/ServDealerMod")
public class ServDealerMod extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpDealers opdeal;
//	private SearchEngine se;
	
	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServDealerMod() {
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
		int param = Integer.parseInt(request.getParameter("id"));
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		Dealers d = opdeal.getSp(param);
		String navimenu = nm.printMenu();
		PrintWriter out = response.getWriter();
		out.println(navimenu);
		out.println("<h1>Modify Dealer</h1>");
		out.println("<form name='frmDealerMod' action='/PCFactory/ServDealerModProcessing?id=" + param + "' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 70%'>");

		out.println("<tr><td style='width: 20%'>DealerID</td><td style='width: 80%'>" + d.getDealerid() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Name</td><td style='width: 80%'><input type='text' id='tbName' name='tbName' value='" + d.getName() + "' style='width: 300px'></td></tr>");
		out.println("<tr><td style='width: 20%'>Phone</td><td style='width: 80%'><input type='text' id='tbPhone' name='tbPhone' value='" + d.getPhone() + "'></td></tr>");
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

}
