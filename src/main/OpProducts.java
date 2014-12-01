package main;
/**
 * 
 * An implementation of Products operation utilities.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class OpProducts {
	private Connection conn;
	
	public OpProducts(Connection conn) {
		this.conn = conn;
	}
	
	public Products getView(int id) {
		Statement st = null;
		Products prod = new Products();
		try {
			String query = "select P.pid, P.name, P.stock, P.isdel, COUNT(*) cnum from Products P, Components C, PRODUCTS_MAKE_OF PMO where P.pid=PMO.pid and PMO.cid=C.cid and P.pid=" + id + " group by P.pid, P.name, P.stock, P.isdel";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				prod.setPid(id);
				prod.setName(rs.getString("name"));
				prod.setStock(rs.getInt("stock"));
				prod.setCompnum(rs.getInt("cnum"));
				prod.setIsdel(rs.getInt("isdel"));
				String subquery = "select E.name ename from PRODUCTS P, Employees E where P.ssn=E.ssn and P.pid=" + id;
				ResultSet rs2 = st.executeQuery(subquery);
				if(rs2.next()) {
					prod.setEname(rs2.getString("ename"));
				}
				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return prod;
	}
	
	public ArrayList getList() {
		ArrayList prodList = new ArrayList();
		Statement st = null;
		try {
			String query = "select pid, name, stock, isdel from Products";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Products prod = new Products();
					prod.setPid(rs.getInt("pid"));
					prod.setName(rs.getString("name"));
					prod.setStock(rs.getInt("stock"));
					prod.setIsdel(0);
					prodList.add(prod);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return prodList;
	}
	
	public ArrayList getCompList(int id) {
		ArrayList compList = new ArrayList();
		Statement st = null;
		try {
			String query = "select C.cid, C.name cname, C.stock, C.price, C.isdel, CL.name lname from Products_Make_Of PMO, Components C, Comp_Location CL where PMO.cid=C.cid and C.lid=CL.lid and PMO.pid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Components comp = new Components();
				comp.setCid(rs.getInt("cid"));
				comp.setName(rs.getString("cname"));
				comp.setLoca(rs.getString("lname"));
				comp.setStock(rs.getInt("stock"));
				comp.setPrice(rs.getInt("price"));
				comp.setIsdel(rs.getInt("isdel"));
				compList.add(comp);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return compList;
	}
	
	public void delProd(int id) {
		Statement st = null;
		try {
			String query = "update Products set isdel=1 where pid=" + id;
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public int addProd(Products p, String ssn) {
		 Statement st = null;
		 int key = -1;
		try {
			String name = p.getName();
			int stock = p.getStock();
			int isdel = 0;
			st = conn.createStatement();
			String query = "insert into Products (name, stock, isdel, ssn) values ('" + name + "', " + stock + ", " + isdel + ", '" + ssn + "')";
			st.executeUpdate(query);
			query = "select MAX(pid) mpid from Products";
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				key = rs.getInt("mpid");
			}
	        rs.close();
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return key;
	}
	
	public void addProdMake(int cid, int pid) {
		Statement st = null;
		try {
			st = conn.createStatement();
			String query = "insert into Products_Make_Of (pid, cid) values (" + pid + ", " + cid + ")";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void delProdMake(int id) {
		Statement st = null;
		try {
			st = conn.createStatement();
			String query = "delete from Products_Make_Of where pid=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void modProd(Products p, int id) {
		Statement st = null;
		try {
			String name = p.getName();
			int stock = p.getStock();
			String ssn = p.getSsn();
			st = conn.createStatement();
			String query = "update Products set name='" + name + "', stock=" + stock + ", ssn='" + ssn + "' where pid=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public Products getSp(int id) {
		Statement st = null;
		Products prod = new Products();
		try {
			String query = "select P.pid, P.name, P.stock, P.isdel, P.ssn from Products P where pid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				prod.setPid(id);
				prod.setName(rs.getString("name"));
				prod.setStock(rs.getInt("stock"));
				prod.setIsdel(rs.getInt("isdel"));
				prod.setSsn(rs.getString("ssn"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return prod;
	}
	
	public boolean isInMakeof(int pid, int cid) {
		Statement st = null;
		int i = 0;
		try {
			String query = "select * from Products_Make_Of where pid=" + pid + " and cid=" + cid;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				i = 1;
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		if(i == 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
