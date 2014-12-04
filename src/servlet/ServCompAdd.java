package servlet;

/**
 * Servlet implementation class ServCompAdd
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Category;

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
import operation.OpComponents;

//@WebServlet("/ServCompAdd")
public class ServCompAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;

	private DBSwitch dbswitch;
	private OpComponents opcomp;
	// private SearchEngine se;

	private String message;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServCompAdd() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		dbswitch = new DBSwitch();
		message = dbswitch.openDBConnection();
		conn = dbswitch.get_conn();
		opcomp = new OpComponents(conn);
		// se = new SearchEngine(conn);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		dbswitch.closeDBConnection();
	}

//	 /**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 response)
//	 */
//	 protected void doGet(HttpServletRequest request, HttpServletResponse
//	 response) throws ServletException, IOException {
//	 response.setContentType("text/html");
//	 String auth = (String) request.getSession(true).getAttribute("auth");
//	 String dept = (String) request.getSession(true).getAttribute("dept");
//	 String ssn = (String) request.getSession(true).getAttribute("ssn");
//	 NaviMenu nm = new NaviMenu(auth, dept, ssn);
//	 String navimenu = nm.printMenu();
//	 PrintWriter out = response.getWriter();
//	 out.println(navimenu);
//	 out.println("<h1>Create Component</h1>");
//	 out.println("<form name='frmCompAdd' action='/PCFactory/ServCompAddProcessing' method='post'>");
//	 out.println("<table align='center' border='1' style='border: 1px solid black; width: 80%'>");
//	
//	 out.println("<tr><td align='center'>Component Name:<input type='text' id='tbName' name='tbName' value='' style='width: 500px'></tr>");
//	 out.println("<tr><td align='center'>Stock:<input type='text' id='tbStock' name='tbStock' value=''></tr>");
//	 out.println("<tr><td align='center'>Price:<input type='text' id='tbPrice' name='tbPrice' value=''></tr>");
//	 if (!message.equalsIgnoreCase("servus")) {
//	 out.println("<h1>Oracle connection failed " + message + "</h1>");
//	 } else {
//	 out.println(getCateListHtml());
//	 }
//	 out.println("<tr><td colspan='2' align='center'><input type='submit' name='btnSubmit' value='Confirm'></td></tr>");
//	
//	 out.println("</table>");
//	 out.println("</form>");
//	 out.println("</body>");
//	 out.println("</html>");
//	 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String auth = (String) request.getSession(true).getAttribute("auth");
		String dept = (String) request.getSession(true).getAttribute("dept");
		String ssn = (String) request.getSession(true).getAttribute("ssn");
		// menu
		String strMenu = "<html ng-app='componentApp'><head><title>PCFactory Management System</title><link href='/PCFactory/style.css' rel='stylesheet' type='text/css' />\r\n";
		strMenu += "<script src='/PCFactory/angular.min.js'></script>\r\n";
		strMenu += "<script>"
				+ "var componentApp = angular.module('componentApp', []);"
				+ "componentApp.controller('componentCtrl', function ($scope, $http){"
				+ "$http.get('/PCFactory/categories.json').success(function(data) {"
				+ "$scope.categories = data;" + "});" + "});" + "</script></head>";
		strMenu += "<body style='text-align: center' ng-controller='componentCtrl'>";
		strMenu += "<div class='nav'><div class='navinner'><ul class='navlist'><li><a href='/PCFactory/ServIndex'>Homepage</a></li>";
		try {
			if (dept.startsWith("General") || dept.startsWith("Executive")) {
				strMenu += "<li><a href='/PCFactory/ServAuthList'>Titles</a></li> <li><a href='/PCFactory/ServEmpList'>Employees</a></li> <li><a href='/PCFactory/ServDeptList'>Departments</a></li> <li><a href='/PCFactory/ServCompList'>Components</a></li> <li><a href='/PCFactory/ServProdList'>Products</a></li> <li><a href='/PCFactory/ServDealerList'>Dealers</a> </li> <li><a href='/PCFactory/ServSalerdList'>SalesRecords</a></li> <li><a href='/PCFactory/ServStat'>Statistics</a></li>";
			} else {
				if (dept.startsWith("Human")) {
					strMenu += "<li><a href='/PCFactory/ServAuthList'>Titles</a></li>";
					strMenu += "<li><a href='/PCFactory/ServEmpList'>Employees</a></li>";
					strMenu += "<li><a href='/PCFactory/ServDeptList'>Departments</a></li>";
				} else {
					strMenu += "<li style='color: white'>Titles</li>";
					strMenu += "<li style='color: white'>Employees<li>";
					strMenu += "<li style='color: white'>Departments</li>";
				}
				if (dept.startsWith("Purchasing")) {
					strMenu += "<li><a href='/PCFactory/ServCompList'>Components</a></li>";
				} else {
					strMenu += "<li style='color: white'>Components</li>";
				}
				if (dept.startsWith("Production")) {
					strMenu += "<li><a href='/PCFactory/ServProdList'>Products</a></li>";
				} else {
					strMenu += "<li style='color: white'>Products</li>";
				}
				if (dept.startsWith("Contract")) {
					strMenu += "<li><a href='/PCFactory/ServDealerList'>Dealers</a></li>";
					strMenu += "<li><a href='/PCFactory/ServSalerdList'>SalesRecords</a></li>";
				} else {
					strMenu += "<li style='color: white'>Dealers</li>";
					strMenu += "<li style='color: white'>SalesRecords</li>";
				}
				if (dept.startsWith("Finance") || dept.startsWith("Accounting")) {
					strMenu += "<li><a href='/PCFactory/ServStat'>Statistics</a></li>";
				} else {
					strMenu += "<li style='color: white'>Statistics</li>";
				}
			}
		} catch (Exception e) {
			// strMenu +=
			// "<li><a href='/PCFactory/ServAuthList'>Titles</a></li> <li><a href='/PCFactory/ServEmpList'>Employees</a></li> <li><a href='/PCFactory/ServDeptList'>Departments</a></li> <li><a href='/PCFactory/ServCompList'>Components</a></li> <li><a href='/PCFactory/ServProdList'>Products</a></li> <li><a href='/PCFactory/ServDealerList'>Dealers</a> </li> <li><a href='/PCFactory/ServSalerdList'>SalesRecords</a></li> <li><a href='/PCFactory/ServStat'>Statistics</a></li>";
		}
		strMenu += "<li><a href='/PCFactory/ServEmpView?id=" + ssn
				+ "'>Information</a></li>";
		strMenu += "<li><a href='/PCFactory/ServExit'>Logout</a>";
		strMenu += "</ul></div></div>";
		PrintWriter out = response.getWriter();
		out.println(strMenu);
		out.println("<h1>Create Component</h1>");
		out.println("<form name='frmCompAdd' action='/PCFactory/ServCompAddProcessing' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 80%'>");

		out.println("<tr><td align='center'>Component Name:<input type='text' id='tbName' name='tbName' value='' style='width: 500px'></tr>");
		out.println("<tr><td align='center'>Stock:<input type='text' id='tbStock' name='tbStock' value=''></tr>");
		out.println("<tr><td align='center'>Price:<input type='text' id='tbPrice' name='tbPrice' value=''></tr>");
		
		out.println("<tr><td align='center'>Category:<select id='Category' name='Category'>");
		out.println("<option ng-repeat='category in categories' value='{{category.id}}'>{{category.name}}</option>");
		out.println("</select></td></tr>");
		
		out.println("<tr><td colspan='2' align='center'><input type='submit' name='btnSubmit' value='Confirm'></td></tr>");

		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected String getCateListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList catelist = opcomp.getCateList();
		builder.append("<tr>");
		builder.append("<td align='center'>Category:<select id='Category' name='Category'>");
		for (int j = 0; j < catelist.size(); j++) {
			Category cate = (Category) catelist.get(j);
			builder.append("<option value='" + cate.lid + "'>" + cate.name
					+ "</option>");
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}

}
