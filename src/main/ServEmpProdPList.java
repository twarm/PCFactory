package main;
/**
 * Servlet implementation class ServEmpProd, relationship between Employees and produced products
 * @author Jianyu Wang(jw3236@drexel.edu)
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

//@WebServlet("/ServEmpProd")
public class ServEmpProdPList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpEmployees opemp;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServEmpProdPList() {
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
		String param = request.getParameter("id");
		NaviMenu nm = new NaviMenu(auth, dept, ssn);
		String navimenu = nm.printMenu();
	    PrintWriter out = response.getWriter();
	    //out.println("<html><head><link href='css/style.css' rel='stylesheet' type='text/css' /></head>");
	    out.println(navimenu);
		out.println("<h1>List of Products which were installed by this Employee</h1>");
		out.println("<table align='center' border='1' style='border: 1px solid black; width: 50%'>");
		
		out.println("<tr><td>PID</td><td>Name</td><td>Stock</td><td>Operations</td></tr>");
		if (!message.equalsIgnoreCase("servus")) {
	    	out.println("<h1>Oracle connection failed " + message + "</h1>");
	    } 
	    else {
		    printEmpProdList(out, param);
	    }
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

	public void printEmpProdList(PrintWriter out, String param) {
	    ArrayList empprodList = opemp.getEmpProdPList(param);
	    for(int i = 0; i < empprodList.size(); i++) {
	    	EmpProduce empprod = (EmpProduce) empprodList.get(i);
	    	out.println(empprod.toHTMLFromEmp());
	    }
	}
}
