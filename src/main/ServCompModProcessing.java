package main;
/**
 * Servlet implementation class ServCompModProcessing
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


//@WebServlet("/ServCompModProcessing")
public class ServCompModProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpComponents opcomp;
//	private SearchEngine se;

	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServCompModProcessing() {
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
		Components comp = new Components();
		int param = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("tbName");
		int stock = Integer.parseInt(request.getParameter("tbStock"));
		double price = Double.parseDouble(request.getParameter("tbPrice"));
		int lid = Integer.parseInt(request.getParameter("Category"));
		comp.setName(name);
		comp.setStock(stock);
		comp.setPrice(price);
		comp.setLid(lid);
		if (!message.equalsIgnoreCase("servus")) {	
			PrintWriter out = response.getWriter();
			out.println("<h1>Oracle connection failed " + message + "</h1>");
		} 
		else {
			opcomp.modComp(comp, param);
			response.sendRedirect("/PCFactory/ServCompList");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
