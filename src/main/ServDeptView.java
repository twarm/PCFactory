package main;
/**
 * Servlet implementation class ServDeptView, information of a specific Department
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

//@WebServlet("/ServDeptView")
public class ServDeptView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpDepartments opdept;
	
	private String message;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServDeptView() {
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
		opdept = new OpDepartments(conn);
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
		Departments dview = opdept.getView(param);
		String status = "";
		if(dview.getIsdel() == 0) {
			status = "Running";
		}
		else {
			status = "Closed";
		}
		out.println("<h1>Department Infomation</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		out.println("<tr><td style='width: 20%'>Name</td><td style='width: 80%'>" + dview.getName() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Budget</td><td style='width: 80%'>" + dview.getBudget() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Manager</td><td style='width: 80%'>" + dview.getManager() + " <a href='ServDeptHist?id=" + param + "' style='text-decoration: underline'>ViewHistory</a></td></tr>");
		out.println("<tr><td style='width: 20%'>Employees Number</td><td style='width: 80%'>" + dview.getEmpnum() + " <a href='ServDeptRoster?id=" + param + "' style='text-decoration: underline'>ViewAll</a></td></tr>");
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
