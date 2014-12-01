package main;
/**
 * Servlet implementation class ServProdList, list of Products
 * @author Jianyu Wang(@jw3236@drexel.edu), Zhichao Cao (zc77@drexel.edu)
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

//@WebServlet("/ServProdList")
public class ServProdAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpProducts opprod;
	private OpComponents opcomp;
	private OpEmployees opemp;
	//private SearchEngine se;

	private String message;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServProdAdd() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 
		dbswitch = new DBSwitch();
		message = dbswitch.openDBConnection();
		conn = dbswitch.get_conn();
		opprod = new OpProducts(conn);
		opcomp = new OpComponents(conn);
		opemp = new OpEmployees(conn);
		//se = new SearchEngine(conn);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		dbswitch.closeDBConnection();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		out.println("<h1>Create Product</h1>");
		out.println("<form name='frmProdAdd' action='/PCFactory/ServProdAddProcessing' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 85%'>");

		if (!message.equalsIgnoreCase("servus")) {
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} else {
			out.println(getComponentsListHtml());
			out.println(getEmployeesListHtml());			
		}
		out.println("<tr><td colspan='2'>Product Name:<input type='text' id='tbName' name='tbName' value='' style='width: 400px'> Number:<input type='text' id='tbNum' name='tbNum'></td></tr>"
				+ "<tr><td colspan='2' align='center'><input type='submit' name='btnSubmit' value='Confirm'></td></tr>");

		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected String getComponentsListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList catlist = opcomp.getCateList();
		ArrayList complist = opcomp.getPAList();
		for (int i = 0; i < catlist.size(); i++) {
			Category cate = (Category) catlist.get(i);
			builder.append("<tr>");
			builder.append("<td width='40%'>" + cate.name + "</td>");
			builder.append("<td><select name='" + cate.getName() + "' id='" + cate.getName() + "'>");
			builder.append("<option value='0'>Nothing</option>");
			for (int j = 0; j < complist.size(); j++) {
				Components comp = (Components) complist.get(j);
				if(comp.lid == cate.lid) {
					builder.append("<option value='" + comp.cid + "'>" + comp.name + "     Stock: " + comp.stock + "</option>");
				}
			}
			builder.append("</select>");
			builder.append("</td>");
			builder.append("</tr>");
		}
		return builder.toString();
	}
	
	protected String getEmployeesListHtml() {
		StringBuffer builder = new StringBuffer();
		ArrayList emplist = opemp.getSpecificList("Production");
		builder.append("<tr>");
		builder.append("<td width='40%'>Employee in Charge</td>");
		builder.append("<td><select id='Employees' name='Employees'>");
		for (int j = 0; j < emplist.size(); j++) {
			Employees emp = (Employees) emplist.get(j);
			builder.append("<option value='" + emp.ssn + "'>" + emp.name + "</option>");
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}
}
