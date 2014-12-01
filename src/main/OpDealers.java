package main;
/**
 * 
 * An implementation of Dealers operation utilities.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OpDealers {
	private Connection conn;

	public OpDealers(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList getList() {
		ArrayList dealList = new ArrayList();
		Statement st = null;
		try {
			String query = "select * from Dealers";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Dealers deal = new Dealers();
					deal.setDealerid(rs.getInt("dealerid"));
					deal.setName(rs.getString("name"));
					deal.setPhone(rs.getString("phone"));
					deal.setIsdel(0);
					dealList.add(deal);
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
	
	public ArrayList getSalerdList(int id) {
		ArrayList salerdList = new ArrayList();
		Statement st = null;
		try {
			String query = "select S.sid, S.datetime, S.num, S.price, S.isdel from SalesRecords S, Products P where P.pid=S.pid and S.dealerid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				SalesRecords salerd = new SalesRecords();
				salerd.setSid(rs.getInt("sid"));
				salerd.setDatetime(rs.getDate("datetime"));
				salerd.setNum(rs.getInt("num"));
				salerd.setPrice(rs.getDouble("price"));
				salerd.setIsdel(rs.getInt("isdel"));
				salerdList.add(salerd);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return salerdList;
	}
	
	public Dealers getView(int id) {
		Statement st = null;
		Dealers dealer = new Dealers();
		try {
			String query = "select D.dealerid, D.name, D.phone, D.isdel from Dealers D where D.dealerid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				dealer.setDealerid(id);
				dealer.setName(rs.getString("name"));
				dealer.setPhone(rs.getString("phone"));
				dealer.setSnum(0);
				dealer.setIsdel(rs.getInt("isdel"));
				String subquery = "select D.dealerid, D.name, D.phone, D.isdel, COUNT(*) snum from Dealers D, SalesRecords S where D.dealerid=S.dealerid and D.dealerid=" + id + " group by D.dealerid, D.name, D.phone, D.isdel";
				ResultSet rs2 = st.executeQuery(subquery);
				if(rs2.next()) {
					dealer.setSnum(rs2.getInt("snum"));
				}
				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return dealer;
	}
	
	public void delDealer(int id) {
		Statement st = null;
		try {
			String query = "update Dealers set isdel=1 where dealerid=" + id;
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void addDealer(String name, String phone) {
		Statement st = null;
		try {
			st = conn.createStatement();
			String query = "insert into Dealers (name, phone, isdel) values ('" + name + "', '" + phone + "', 0)";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void modDealer(String name, String phone, int id) {
		Statement st = null;
		try {
			st = conn.createStatement();
//			String query = "insert into Dealers (name, phone, isdel) values ('" + name + "', '" + phone + "', 0)";
			String query = "update Dealers set name='" + name + "', phone='" + phone + "' where dealerid=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public Dealers getSp(int id) {
		Statement st = null;
		Dealers dealer = new Dealers();
		try {
			String query = "select D.dealerid, D.name, D.phone, D.isdel from Dealers D where D.dealerid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				dealer.setDealerid(id);
				dealer.setName(rs.getString("name"));
				dealer.setPhone(rs.getString("phone"));
				dealer.setSnum(0);
				dealer.setIsdel(rs.getInt("isdel"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return dealer;
	}
}
