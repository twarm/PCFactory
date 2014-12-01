package main;
/**
 * Servlet implementation class ServEmpView, information of a specific employee
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

//@WebServlet("/ServEmpView")
public class ServEmpView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpEmployees opemp;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServEmpView() {
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
		opemp = new OpEmployees(conn);
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
		String param = request.getParameter("id");
		out.println(navimenu);
		Employees eview = opemp.getView(param);
		String status = "";
		if(eview.getIsdel() == 0) {
			status = "Working";
		}
		else {
			status = "Resigned";
		}
		out.println("<h1>Employee Infomation</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		out.println("<tr><td style='width: 20%'>SSN</td><td style='width: 80%'>" + eview.getSsn() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Name</td><td style='width: 80%'>" + eview.getName() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Title</td><td style='width: 80%'>" + eview.getAuth() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Department</td><td style='width: 80%'>" + eview.getDepart() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Work Since</td><td style='width: 80%'>" + eview.getSince() + "</td></tr>");
		out.println("<tr><td style='width: 20%'>Number of Products</td><td style='width: 80%'>" + eview.getProdnum() + " <a href='ServEmpProdPList?id=" + param + "' style='text-decoration: underline'>ViewAll</a></td></tr>");
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
