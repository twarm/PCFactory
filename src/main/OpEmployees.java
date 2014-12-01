package main;
/**
 * 
 * An implementation of employees operation utilities.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OpEmployees {
	private Connection conn;
	
	public OpEmployees(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList getEmpProdEList(int id) {
		ArrayList epList = new ArrayList();
		Statement st = null;
		try {
			String query = "select E.ssn, E.name ename, A.name, E.isdel aname from Employees E, Authorizations A, EMPLOYEES_PRODUCE EP where E.aid=A.aid and E.ssn=EP.ssn and EP.pid=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					EmpProduce ep = new EmpProduce();
					ep.setAuth("aname");
					ep.setEmp(rs.getString("ename"));
					ep.setSsn(rs.getString("ssn"));
					epList.add(ep);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return epList;
	}
	
	public ArrayList getEmpProdPList(String id) {
		ArrayList epList = new ArrayList();
		Statement st = null;
		try {
			String query = "select P.pid, P.name, P.stock, P.isdel from Products P where P.ssn=" + id;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				EmpProduce ep = new EmpProduce();
				ep.setPid(rs.getInt("pid"));
				ep.setProduct(rs.getString("name"));
				ep.setStock(rs.getInt("stock"));
				epList.add(ep);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return epList;
	}
	
	public ArrayList getList() {
		ArrayList empList = new ArrayList();
		Statement st = null;
		try {
			String query = "select E.ssn, E.name ename, E.since, E.isdel, D.name dname, A.name aname from Employees E, Authorizations A, Departments D where E.aid=A.aid and E.did=D.did";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Employees emp = new Employees();
					emp.setSsn(rs.getString("ssn"));
					emp.setName(rs.getString("ename"));
					emp.setSince(rs.getDate("since"));
					emp.setDepart(rs.getString("dname"));
					emp.setAuth(rs.getString("aname"));
					emp.isdel = 0;
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
	
	public Employees getView(String paramSSN) {
		Statement st = null;
		Employees emp = new Employees();
		try {
			String query = "select E.ssn, E.name ename, E.since, E.isdel, D.name dname, A.name aname from Employees E, Authorizations A, Departments D where E.aid=A.aid and E.did=D.did and ssn='" + paramSSN + "'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				emp.setSsn(rs.getString("ssn"));
				emp.setName(rs.getString("ename"));
				emp.setSince(rs.getDate("since"));
				emp.setDepart(rs.getString("dname"));
				emp.setAuth(rs.getString("aname"));
				emp.setIsdel(rs.getInt("isdel"));
				emp.setProdnum(0);
				String subquery = "select P.ssn, COUNT(*) pnum from Products P where P.ssn='" + paramSSN + "' group by P.ssn";
				ResultSet rs2 = st.executeQuery(subquery);
				if(rs2.next()) {
					emp.setProdnum(rs2.getInt("pnum"));
				}
				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return emp;
	}
	
	public Employees getSp(String ssn) {
		Statement st = null;
		Employees emp = new Employees();
		try {
			String query = "select * from Employees where ssn='" + ssn + "'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				emp.setSsn(rs.getString("ssn"));
				emp.setName(rs.getString("name"));
				emp.setSince(rs.getDate("since"));
				emp.setDid(rs.getInt("did"));
				emp.setAid(rs.getInt("aid"));
				emp.setPw(rs.getString("pw"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return emp;
	}
	
	public boolean isManager(String ssn, int did) {
		Statement st = null;
		int i = 0;
		try {
			String query = "select * from Departments_Managers where did=" + did + " order by time_to desc";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				if(ssn.startsWith(rs.getString("ssn"))) {
					i = 1;
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		if(i == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void delEmp(String id) {
		Statement st = null;
		try {
			String query = "update Employees set isdel=1 where ssn='" + id + "'";
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public ArrayList getSpecificList(String dept) {
		ArrayList empList = new ArrayList();
		Statement st = null;
		try {
			String query = "select E.ssn, E.name, E.isdel from Employees E, Departments D where E.did=D.did and D.name like '" + dept + "%'";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					Employees emp = new Employees();
					emp.setSsn(rs.getString("ssn"));
					emp.setName(rs.getString("name"));
					emp.setIsdel(rs.getInt("isdel"));;
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
	
	public void addEmp(Employees emp) {
		Statement st = null;
		try {
			String ssn = emp.getSsn();
			String name = emp.getName();
			String pw = emp.getPw();
			Date now = emp.getSince();
			int aid = emp.getAid();
			int did = emp.getDid();
			int isdel = 0;
			st = conn.createStatement();
			String query = "insert into Employees (ssn, name, aid, did, since, pw, isdel) values ( '" + ssn + "', '" + name + "', " + aid + ", " + did + ", '"+(new SimpleDateFormat("")).format(now)+"', '" + pw + "', " + isdel + ")";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void modEmp(Employees emp, String ssn) {
		Statement st = null;
		try {
			String newssn = emp.getSsn();
			String name = emp.getName();
			String pw = emp.getPw();
			int aid = emp.getAid();
			int did = emp.getDid();
			st = conn.createStatement();
//			String query = "insert into Employees (ssn, name, aid, did, since, pw, isdel) values ( '" + ssn + "', '" + name + "', " + aid + ", " + did + ", TO_DATE('" + since + "', 'YYYY-MM-DD HH24:MI:SS'), '" + pw + "', " + isdel + ")";
			String query = "update Employees set ssn='" + newssn + "', name='" + name + "', pw='" + pw + "', aid=" + aid + ", did=" + did + " where ssn='" + ssn + "'";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
