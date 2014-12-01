package main;
/**
 * Servlet implementation class ServDealerView, information of a specific Dealer
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/ServDealerView")
public class ServDealerView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpDealers opdeal;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServDealerView() {
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
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
		out.println(navimenu);
		Dealers dview = opdeal.getView(param);
		String status = "";
		if(dview.getIsdel() == 0) {
			status = "In Contacted";
		}
		else {
			status = "Deleted";
		}
		out.println("<h1>Dealer Infomation</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		out.println("<tr><td style='width: 20%'>DealerID</td><td style='width: 80%'>" + dview.getDealerid() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Name</td><td style='width: 80%'>" + dview.getName() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Phone</td><td style='width: 80%'>" + dview.getPhone() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Number of Sales Records</td><td style='width: 80%'>" + dview.getSnum() + " <a href='ServDealerSList?id=" + param + "' style='text-decoration: underline'>ViewAll</a></td></tr>");
		out.println("<tr><td style='width: 20%'>Phone</td><td style='width: 80%'>" + status + "</td></tr>");
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
