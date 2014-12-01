package main;
/**
 * 
 * An implementation of Components operation utilities.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OpComponents {
	private Connection conn;
	
	public OpComponents(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList getList() {
		ArrayList compList = new ArrayList();
		Statement st = null;
		try {
			String query = "select cid, C.name cname, stock, price, C.isdel, CL.name lname, C.lid from Components C, Comp_Location CL where C.lid=CL.lid";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Components comp = new Components();
					comp.setCid(rs.getInt("cid"));
					comp.setName(rs.getString("cname"));
					comp.setLoca(rs.getString("lname"));
					comp.setStock(rs.getInt("stock"));
					comp.setPrice(rs.getDouble("price"));
					comp.setIsdel(0);
					compList.add(comp);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return compList;
	}
	
	public Components getView(int id) {
		Statement st = null;
		Components comp = new Components();
		try {
			String query = "select C.cid, C.name, C.stock, C.price, CL.name loca , C.isdel from Components C, Comp_Location CL where C.lid=CL.lid and C.cid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				comp.setCid(id);
				comp.setName(rs.getString("name"));
				comp.setStock(rs.getInt("stock"));
				comp.setPrice(rs.getInt("price"));
				comp.setLoca(rs.getString("loca"));
				comp.setIsdel(rs.getInt("isdel"));
				comp.setPnum(0);
				String subquery = "select C.cid, COUNT(*) pnum from Products_Make_Of PMO, Components C where PMO.cid=C.cid and C.cid=" + id + " group by C.cid";
				ResultSet rs2 = st.executeQuery(subquery);
				if(rs2.next()) {
					comp.setPnum(rs2.getInt("pnum"));
				}
				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return comp;
	}
	
	public ArrayList getProdList(int id) {
		ArrayList prodList = new ArrayList();
		Statement st = null;
		try {
			String query = "select P.pid, P.name, P.stock from Products P, Products_Make_Of PMO where P.pid=PMO.pid and PMO.cid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Products prod = new Products();
				prod.setPid(rs.getInt("pid"));
				prod.setName(rs.getString("name"));
				prod.setStock(rs.getInt("stock"));
				prodList.add(prod);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return prodList;
	}
	
	public void delComp(int id) {
		Statement st = null;
		try {
			String query = "update Components set isdel=1 where cid=" + id;
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public ArrayList getPriceList(int pid) {
		Statement st = null;
		ArrayList compList = new ArrayList();
		try {
			String query = "select C.name, C.price from Components C, Products_Make_Of PMO where C.cid=PMO.cid and PMO.pid=" + pid;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Components comp = new Components();
				comp.setName(rs.getString("name"));
				comp.setPrice(rs.getInt("price"));
				comp.setIsdel(0);
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
	
	public int getCateNum() {
		int num = 0;
		Statement st = null;
		ArrayList compList = new ArrayList();
		try {
			String query = "select COUNT(*) sum from Comp_Location";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				num = rs.getInt("sum");
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return num;
	}
	
	public ArrayList getCateList() {
		ArrayList cateList = new ArrayList();
		Statement st = null;
		try {
			String query = "select * from Comp_Location";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Category cate = new Category();
					cate.setLid(rs.getInt("lid"));
					cate.setName(rs.getString("name"));
					cate.setIsdel(0);
					cateList.add(cate);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return cateList;
	}
	
	public ArrayList getPAList() {
		ArrayList compList = new ArrayList();
		Statement st = null;
		try {
			String query = "select cid, C.name, stock, price, C.isdel, C.lid from Components C where stock > 0";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Components comp = new Components();
					comp.setCid(rs.getInt("cid"));
					comp.setName(rs.getString("name"));
					comp.setStock(rs.getInt("stock"));
					comp.setPrice(rs.getDouble("price"));
					comp.setLid(rs.getInt("lid"));
					comp.setIsdel(0);
					compList.add(comp);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return compList;
	}
	
	public void addComp(Components c) {
		 Statement st = null;
		try {
			String name = c.getName();
			int stock = c.getStock();
			double price = c.getPrice();
			int isdel = 0;
			int lid = c.getLid();
			st = conn.createStatement();
			String query = "insert into Components (name, stock, price, isdel, lid) values ('" + name + "', " + stock + ", " + price + ", " + isdel + ", " + lid + ")";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public Components getSp(int id) {
		Statement st = null;
		Components comp = new Components();
		try {
			String query = "select C.cid, C.name, C.stock, C.price, CL.name loca , C.isdel, CL.lid from Components C, Comp_Location CL where C.lid=CL.lid and C.cid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				comp.setCid(id);
				comp.setName(rs.getString("name"));
				comp.setStock(rs.getInt("stock"));
				comp.setPrice(rs.getInt("price"));
				comp.setLoca(rs.getString("loca"));
				comp.setLid(rs.getInt("lid"));
				comp.setIsdel(rs.getInt("isdel"));
				comp.setPnum(0);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return comp;
	}
	
	public void modComp(Components c, int id) {
		 Statement st = null;
		try {
			String name = c.getName();
			int stock = c.getStock();
			double price = c.getPrice();
			int lid = c.getLid();
			st = conn.createStatement();
//			String query = "insert into Components (name, stock, price, isdel, lid) values ('" + name + "', " + stock + ", " + price + ", " + isdel + ", " + lid + ")";
			String query = "update Components set name='" + name + "', stock=" + stock + ", price=" + price + ", lid=" + lid + " where cid=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
