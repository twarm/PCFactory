package main;
/**
 * 
 * An implementation of Sales Records operation utilities.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OpSalesRecords {
	private Connection conn;

	public OpSalesRecords(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList getList() {
		ArrayList dealList = new ArrayList();
		Statement st = null;
		try {
			String query = "select * from SalesRecords";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					SalesRecords sr = new SalesRecords();
					sr.setSid(rs.getInt("sid"));
					sr.setDatetime(rs.getDate("datetime"));
					sr.setNum(rs.getInt("num"));
					sr.setPrice(rs.getDouble("price"));
					sr.setIsdel(rs.getInt("isdel"));
					dealList.add(sr);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return dealList;
	}	
	
	public ArrayList getStatList() {
		ArrayList dealList = new ArrayList();
		Statement st = null;
		try {
			String query = "select * from SalesRecords";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					SalesRecords sr = new SalesRecords();
					sr.setSid(rs.getInt("sid"));
					sr.setDatetime(rs.getDate("datetime"));
					int num = rs.getInt("num");
					sr.setNum(num);
					double price = rs.getDouble("price");
					sr.setPrice(price);
					sr.setTotalprice(price * num);
					sr.setIsdel(rs.getInt("isdel"));
					dealList.add(sr);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return dealList;
	}	

	public SalesRecords getView(int id) {
		Statement st = null;
		SalesRecords sr = new SalesRecords();
		try {
			String query = "select S.sid, S.datetime, S.num, S.price, S.isdel, P.name pname, D.name dname from SalesRecords S, Products P, Dealers D where S.dealerid=D.dealerid and S.pid=P.pid and S.sid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				sr.setSid(id);
				sr.setDatetime(rs.getDate("datetime"));
				sr.setNum(rs.getInt("num"));
				sr.setPrice(rs.getDouble("price"));
				sr.setProd(rs.getString("pname"));
				sr.setDealer(rs.getString("dname"));
				sr.setIsdel(rs.getInt("isdel"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return sr;
	}
	
	public void delSalerd(int id) {
		Statement st = null;
		try {
			String query = "update SalesRecords set isdel=1 where sid=" + id;
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public SalesRecords getStatView(int id) {
		Statement st = null;
		SalesRecords sr = new SalesRecords();
		try {
			String query = "select S.sid, S.datetime, S.num, S.price, S.isdel, S.pid, P.name from SalesRecords S, Products P where S.pid=P.pid and S.sid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				int sid = rs.getInt("sid");
				sr.setSid(sid);
				sr.setDatetime(rs.getDate("datetime"));
				int num = rs.getInt("num");
				sr.setNum(num);
				double price = rs.getDouble("price");
				sr.setPid(rs.getInt("pid"));
				sr.setProd(rs.getString("name"));
				sr.setPrice(price);
				sr.setTotalprice(price * num);
				sr.setIsdel(rs.getInt("isdel"));
//				int pid = rs.getInt("pid");
//				String subquery = "select P.pid, COUNT(*) csum from Products P, Products_Make_Of PMO where P.pid=PMO.pid and P.pid=" + pid + " group by P.pid";
//				ResultSet rs2 = st.executeQuery(subquery);
//				if(rs2.next()) {
//					int csum = rs2.getInt("csum");
//					sr.setCnum(csum);
//				}
//				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return sr;
	}
	
	public void addSalerd(SalesRecords sr) {
		Statement st = null;
		try {
			int num = sr.getNum();
			double price = sr.getPrice();
			Date datetime = sr.getDatetime();
			int pid = sr.getPid();
			int dealerid = sr.getDealerid();
			int isdel = 0;
			st = conn.createStatement();
			String query = "insert into SalesRecords (datetime, num, price, dealerid, pid, isdel) values ( '" + datetime + "', " + num + ", " + price + ", " + dealerid + ", " + pid + ", " + isdel + ")";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void modSalerd(SalesRecords sr, int id) {
		Statement st = null;
		try {
			int num = sr.getNum();
			double price = sr.getPrice();
			int pid = sr.getPid();
			int dealerid = sr.getDealerid();
			st = conn.createStatement();
//			String query = "insert into SalesRecords (datetime, num, price, dealerid, pid, isdel) values ( TO_DATE('" + datetime + "', 'YYYY-MM-DD HH24:MI:SS'), " + num + ", " + price + ", " + dealerid + ", " + pid + ", " + isdel + ")";
			String query = "update SalesRecords set num=" + num + ", price=" + price + ", pid=" + pid + ", dealerid=" + dealerid + " where sid=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public SalesRecords getSp(int id) {
		Statement st = null;
		SalesRecords sr = new SalesRecords();
		try {
			String query = "select S.sid, S.datetime, S.num, S.price, S.isdel, P.name pname, D.name dname, S.pid, S.dealerid from SalesRecords S, Products P, Dealers D where S.dealerid=D.dealerid and S.pid=P.pid and S.sid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				sr.setSid(id);
				sr.setDatetime(rs.getDate("datetime"));
				sr.setNum(rs.getInt("num"));
				sr.setPrice(rs.getDouble("price"));
				sr.setProd(rs.getString("pname"));
				sr.setDealer(rs.getString("dname"));
				sr.setIsdel(rs.getInt("isdel"));
				sr.setPid(rs.getInt("pid"));
				sr.setDealerid(rs.getInt("dealerid"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return sr;
	}
}
