package servlet;
/**
 * Servlet implementation class ServCompView, information of a specific Components
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Components;

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
import operation.OpComponents;

//@WebServlet("/ServCompView")
public class ServCompView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpComponents opcomp;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServCompView() {
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
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
		int param = Integer.parseInt(request.getParameter("id"));
		out.println(navimenu);
		Components cview = opcomp.getView(param);
		String status = "";
		if(cview.getIsdel() == 0) {
			status = "In List";
		}
		else {
			status = "Deleted";
		}
		out.println("<h1>Component Infomation</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		out.println("<tr><td style='width: 20%'>CID</td><td style='width: 80%'>" + cview.getCid() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Name</td><td style='width: 80%'>" + cview.getName() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Stock</td><td style='width: 80%'>" + cview.getStock() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Price</td><td style='width: 80%'>" + cview.getPrice() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Number of Products Using</td><td style='width: 80%'>" + cview.getPnum() + " <a href='ServCompPRoster?id=" + param + "' style='text-decoration: underline'>ViewAll</a></td></tr>");
		out.println("<tr><td style='width: 20%'>Category</td><td style='width: 80%'>" + cview.getLoca() + "</td></tr>");
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
