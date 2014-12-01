package servlet;
/**
 * Servlet implementation class ServEmpAdd
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Authorizations;
import items.Departments;

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
import operation.OpAuthorizations;
import operation.OpDepartments;
import operation.OpEmployees;


//@WebServlet("/ServEmpAdd")
public class ServEmpAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpEmployees opemp;
	private OpDepartments opdept;
	private OpAuthorizations opauth;
//	private SearchEngine se;

	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServEmpAdd() {
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
		opdept = new OpDepartments(conn);
		opauth = new OpAuthorizations(conn);
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
		out.println("<h1>Add Employee</h1>");
		out.println("<form name='frmEmpAdd' action='/PCFactory/ServEmpAddProcessing' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");

		out.println("<tr><td width='40%' align='center'>SSN</td><td><input type='text' id='tbSSN' name='tbSSN' size='9' maxlength='9' value=''></tr>");
		out.println("<tr><td width='40%' align='center'>Name</td><td><input type='text' id='tbName' name='tbName' value=''></tr>");
		out.println("<tr><td width='40%' align='center'>Password</td><td><input type='text' id='tbPW' name='tbPW' value=''></tr>");
		if (!message.equalsIgnoreCase("servus")) {
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} else {
			out.println(getDeptListHtml());		
			out.println(getAuthListHtml());
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
	
	protected String getDeptListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList deptlist = opdept.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Department:</td>");
		builder.append("<td><select id='Departments' name='Departments'>");
		for (int j = 0; j < deptlist.size(); j++) {
			Departments dept = (Departments) deptlist.get(j);
			builder.append("<option value='" + dept.did + "'>" + dept.name + "</option>");
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}
	
	protected String getAuthListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList authlist = opauth.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Job Title:</td>");
		builder.append("<td><select id='Auth' name='Auth'>");
		for (int j = 0; j < authlist.size(); j++) {
			Authorizations auth = (Authorizations) authlist.get(j);
			builder.append("<option value='" + auth.aid + "'>" + auth.name + "</option>");
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}

}
