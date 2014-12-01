package main;
/**
 * Servlet implementation class ServSalerdModProcessing
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


//@WebServlet("/ServSalerdModProcessing")
public class ServSalerdModProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpSalesRecords opsr;
//	private SearchEngine se;
	
	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServSalerdModProcessing() {
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
		opsr = new OpSalesRecords(conn);
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
		int param = Integer.parseInt(request.getParameter("id"));
		int num = Integer.parseInt(request.getParameter("tbNum"));
		double price = Double.parseDouble(request.getParameter("tbPrice"));
		int pid = Integer.parseInt(request.getParameter("Products"));
		int dealerid = Integer.parseInt(request.getParameter("Dealers"));
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		SalesRecords sr = new SalesRecords();
		sr.setNum(num);
		sr.setPrice(price);
		sr.setDealerid(dealerid);
		sr.setPid(pid);
		sr.setDatetime(now);
		sr.setIsdel(0);
		if (!message.equalsIgnoreCase("servus")) {	
			PrintWriter out = response.getWriter();
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} 
		else {
			opsr.modSalerd(sr, param);
			response.sendRedirect("/PCFactory/ServSalerdList");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
