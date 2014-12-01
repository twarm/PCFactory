package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/ServAuthAdd")
public class ServAuthAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	private DBSwitch dbswitch;
	private OpAuthorizations opauth;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServAuthAdd() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		dbswitch = new DBSwitch();
		message = dbswitch.openDBConnection();
		conn = dbswitch.get_conn();
		opauth = new OpAuthorizations(conn);
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
		out.println("<h1>Create Job Title</h1>");
		out.println("<form name='frmAuthAdd' action='/PCFactory/ServAuthAddProcessing' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 70%'>");

		out.println("<tr><td colspan='2' align='center'>Title Name:<input type='text' id='tbName' name='tbName' value='' style='width: 300px'></td></tr>"
				+ "<tr><td colspan='2' align='center'><input type='submit' name='btnSubmit' value='Confirm'></td></tr>");

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
