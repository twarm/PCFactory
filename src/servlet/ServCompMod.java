package servlet;
/**
 * Servlet implementation class ServCompMod
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import items.Category;
import items.Components;

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


//@WebServlet("/ServCompMod")
public class ServCompMod extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpComponents opcomp;
//	private SearchEngine se;

	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServCompMod() {
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
		Components comp = opcomp.getSp(param);
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		String navimenu = nm.printMenu();
		PrintWriter out = response.getWriter();
		out.println(navimenu);
		out.println("<h1>Create Component</h1>");
		out.println("<form name='frmCompMod' action='/PCFactory/ServCompModProcessing?id=" + param + "' method='post'>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 80%'>");

		out.println("<tr><td align='center'>Component Name:<input type='text' id='tbName' name='tbName' value='" + comp.getName() + "' style='width: 500px'></tr>");
		out.println("<tr><td align='center'>Stock:<input type='text' id='tbStock' name='tbStock' value='" + comp.getStock() + "'></tr>");
		out.println("<tr><td align='center'>Price:<input type='text' id='tbPrice' name='tbPrice' value='" + comp.getPrice() + "'></tr>");
		if (!message.equalsIgnoreCase("servus")) {
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} else {
			out.println(getCateListHtml(comp.getLid()));
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
	
	protected String getCateListHtml(int id) {
		StringBuffer builder = new StringBuffer();
		ArrayList catelist = opcomp.getCateList();
		builder.append("<tr>");
		builder.append("<td align='center'>Category:<select id='Category' name='Category'>");
		for (int j = 0; j < catelist.size(); j++) {
			Category cate = (Category) catelist.get(j);
			if(cate.lid == id) {
				builder.append("<option selected value='" + cate.lid + "'>" + cate.name + "</option>");
			}
			else {
				builder.append("<option value='" + cate.lid + "'>" + cate.name + "</option>");
			}
		}
		builder.append("</select>");
		builder.append("</td>");
		builder.append("</tr>");
		return builder.toString();
	}

}
