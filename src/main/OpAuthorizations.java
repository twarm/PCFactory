package main;
/**
 * Implementation class of operations on class Authorizations
 * @author Jianyu Wang(jw3236@drexel.edu)
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OpAuthorizations {
	private Connection conn;
	
	public OpAuthorizations(Connection conn) {
		this.conn = conn;
	}

	public Authorizations getView(int id) {
		Statement st = null;
		Authorizations auth = new Authorizations();
		try {
			String query = "select A.aid, A.name, A.isdel from Authorizations A where A.aid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				auth.setAid(id);
				auth.setName(rs.getString("name"));
				auth.setEmpnum(0);
				auth.setIsdel(rs.getInt("isdel"));
				String subquery = "select A.aid, A.name, A.isdel, COUNT(*) enum from Authorizations A, Employees E where E.isdel=0 and E.aid=A.aid and A.aid=" + id + " group by A.aid, A.name, A.isdel";
				ResultSet rs2 = st.executeQuery(subquery);
				if(rs2.next()) {
					auth.setEmpnum(rs2.getInt("enum"));
				}
				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return auth;
	}
	
	public ArrayList getList() {
		ArrayList authList = new ArrayList();
		Statement st = null;
		try {
			String query = "select aid, name, isdel from Authorizations";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Authorizations auth = new Authorizations();
					auth.setAid(rs.getInt("aid"));
					auth.setName(rs.getString("name"));
					auth.setIsdel(0);
					authList.add(auth);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return authList;
	}
	
	public ArrayList getEmpList(int aid) {
		ArrayList empList = new ArrayList();
		Statement st = null;
		try {
			String query = "select ssn, E.name ename, since, A.name aname, E.isdel from Employees E, Authorizations A where E.aid=A.aid and A.aid=" + aid;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Employees emp = new Employees();
					emp.setSsn(rs.getString("ssn"));
					emp.setName(rs.getString("ename"));
					emp.setSince(rs.getDate("since"));
					emp.setAuth(rs.getString("aname"));
					empList.add(emp);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return empList;
	}
	
	public void delAuth(int id) {
		Statement st = null;
		try {
			String query = "update Authorizations set isdel=1 where aid=" + id;
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void addAuth(String name) {
		Statement st = null;
		try {
			st = conn.createStatement();
			String query = "insert into Authorizations (name, isdel) values ('" + name + "', 0)";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public Authorizations getSpAuth(int id) {
		Statement st = null;
		Authorizations auth = new Authorizations();
		try {
			String query = "select A.aid, A.name, A.isdel from Authorizations A where A.aid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				auth.setAid(id);
				auth.setName(rs.getString("name"));
				auth.setIsdel(rs.getInt("isdel"));				
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return auth;
	}
	
	public void modAuth(String name, int id) {
		Statement st = null;
		try {
			st = conn.createStatement();
			String query = "update Authorizations set name='" + name + "' where aid=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
