package servlet;
/**
 * Servlet implementation class ServEmpMod
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Authorizations;
import items.Departments;
import items.Employees;

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


//@WebServlet("/ServEmpMod")
public class ServEmpMod extends HttpServlet {
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
    public ServEmpMod() {
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
		String param = request.getParameter("id");
		Employees emp = opemp.getSp(param);
		String navimenu = nm.printMenu();
		PrintWriter out = response.getWriter();
		out.println(navimenu);
		out.println("<h1>Modify Employee</h1>");
		out.println("<form name='frmEmpMod' action='/PCFactory/ServEmpModProcessing?id=" + param + "' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 60%'>");

		out.println("<tr><td width='40%' align='center'>SSN</td><td><input type='text' id='tbSSN' name='tbSSN' size='9' readonly maxlength='9' value='" + emp.getSsn() + "'></tr>");
		out.println("<tr><td width='40%' align='center'>Name</td><td><input type='text' id='tbName' name='tbName' value='" + emp.getName() + "'></tr>");
		out.println("<tr><td width='40%' align='center'>Since</td><td>" + emp.getSince() + "</tr>");
		out.println("<tr><td width='40%' align='center'>Password</td><td><input type='text' id='tbPW' name='tbPW' value='" + emp.getPw() + "'></tr>");
		
		if (!message.equalsIgnoreCase("servus")) {
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} else {
			out.println(getDeptListHtml(emp.getDid()));		
			out.println(getAuthListHtml(emp.getAid()));
			out.println(getIsManagerHtml(emp.getSsn(), emp.getDid()));
		}
//		out.println("<tr><td width='40%' align='center'>Manager</td><td><input type='checkbox' id='cbManager' name='cbManager' value='Is Manager' /></tr>");
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

	protected String getDeptListHtml(int id) {
		StringBuffer builder = new StringBuffer();
		ArrayList deptlist = opdept.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Department:</td>");
		builder.append("<td><select id='Departments' name='Departments'>");
		for (int j = 0; j < deptlist.size(); j++) {
			Departments dept = (Departments) deptlist.get(j);
			if(id == dept.getDid()) {
				builder.append("<option selected value='" + dept.did + "'>" + dept.name + "</option>");
			}
			else {
				builder.append("<option value='" + dept.did + "'>" + dept.name + "</option>");
			}
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}
	
	protected String getAuthListHtml(int id) {
		StringBuffer builder = new StringBuffer();
		ArrayList authlist = opauth.getList();
		builder.append("<tr>");
		builder.append("<td width='40%' align='center'>Job Title:</td>");
		builder.append("<td><select id='Auth' name='Auth'>");
		for (int j = 0; j < authlist.size(); j++) {
			Authorizations auth = (Authorizations) authlist.get(j);
			if(id == auth.getAid()) {
				builder.append("<option selected value='" + auth.aid + "'>" + auth.name + "</option>");
			}
			else {
				builder.append("<option value='" + auth.aid + "'>" + auth.name + "</option>");
			}
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}
	
	protected String getIsManagerHtml(String ssn, int id) {
		StringBuffer builder = new StringBuffer();
		if(opemp.isManager(ssn, id)) {
			builder.append("<tr><td width='40%' align='center'>Manager</td><td><input type='checkbox' id='cbManager' name='cbManager' value='Is Manager' checked />Is a Manager</tr>");
		}
		else {
			builder.append("<tr><td width='40%' align='center'>Manager</td><td><input type='checkbox' id='cbManager' name='cbManager' value='Is Manager' />Is a Manager</tr>");
		}
		return builder.toString();
	}
}
