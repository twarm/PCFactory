package main;
/**
 * Servlet implementation class ServProdDel
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import java.io.IOException;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/ServProdDel")
public class ServProdDel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	 
	private DBSwitch dbswitch;
	private OpProducts opprod;
//	private SearchEngine se;

	private String message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServProdDel() {
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
		opprod = new OpProducts(conn);
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
		int param = Integer.parseInt(request.getParameter("id"));
		opprod.delProd(param);
		response.sendRedirect("/PCFactory/ServProdList");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
