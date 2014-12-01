package main;
/**
 * 
 * An implementation of Departments operation utilities.
 *  
 * @author Jianyu Wang(jw3236@drexel.edu)
 *
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OpDepartments {
	private Connection conn;

	public OpDepartments(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList getList() {
		ArrayList deptList = new ArrayList();
		Statement st = null;
		try {
			String query = "select * from Departments";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				if(rs.getInt("isdel") == 0) {
					int did = rs.getInt("did");
					String name = rs.getString("name");
					double budget = rs.getDouble("budget");
					int isdel = 0;
					Departments dept = new Departments(did, name, budget, isdel);
					deptList.add(dept);
				}
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return deptList;
	}
	
	public ArrayList getHist(int did) {
		ArrayList histList = new ArrayList();
		Statement st = null;
		try {
			String query = "select time_from, time_to, E.ssn, E.name ename, E.isdel from Employees E, Departments_Managers D where E.ssn=D.ssn and D.did=" + did + "order by time_to desc";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				DepartManager dm = new DepartManager();
				dm.setSsn(rs.getString("ssn"));
				dm.setEmp(rs.getString("ename"));
				dm.setFrom(rs.getDate("time_from"));
				dm.setTo(rs.getDate("time_to"));
				histList.add(dm);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return histList;
	}
	
	public ArrayList getEmpList(int did) {
		ArrayList empList = new ArrayList();
		Statement st = null;
		try {
			String query = "select ssn, E.name ename, since, A.name aname, E.isdel from Employees E, Authorizations A where E.aid=A.aid and did=" + did;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				Employees emp = new Employees();
				emp.setSsn(rs.getString("ssn"));
				emp.setName(rs.getString("ename"));
				emp.setSince(rs.getDate("since"));
				emp.setAuth(rs.getString("aname"));
				emp.setIsdel(rs.getInt("isdel"));
				empList.add(emp);
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return empList;
	}
	
	public Departments getView(int did) {
		Statement st = null;
		Departments dept = new Departments();
		try {
			String query = "select D.name, D.budget, D.isdel, COUNT(*) num from Departments D, Employees E where D.did=" + did + " and D.did=E.did group by D.name, D.budget, D.isdel";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				dept.setDid(did);
				dept.setBudget(rs.getInt("budget"));
				dept.setName(rs.getString("name"));
				dept.setEmpnum(rs.getInt("num"));
				dept.setIsdel(rs.getInt("isdel"));
				dept.setManager("Vacant");
				String subquery = "select E.name, time_to from Departments_Managers D, Employees E where D.ssn=E.ssn and D.did=" + did + " order by time_to desc";
				ResultSet rs2 = st.executeQuery(subquery);
				if(rs2.next()) {
					Date now = new Date();
					if(rs2.getDate("time_to").compareTo(now) > 0) {
						dept.setManager(rs2.getString("name"));
					}
				}
				rs2.close();
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return dept;
	}
	
	public void modDeptManager(String ssn, int did, int i) {
		Statement st = null;
		Date now = new Date();
		try {
			st = conn.createStatement();
			String query = "select * from Departments_Managers where ssn='" + ssn + "' and did=" + did + " order by time_to desc";
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				if(rs.getDate("time_to").compareTo(now) > 0) {
					if(i == 0) {
						String subquery = "update Departments_Managers set time_to=" + (new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS")).format(now) + " where ssn='" + ssn + "' and did=" + did;
						st.executeUpdate(subquery);
					}
				}
				else {
					if(i == 1) {
						String subquery = "select * from Departments_Managers D where D.did=" + did + " order by time_to desc";
						ResultSet rs2 = st.executeQuery(subquery);
						if(rs2.next()) {
							if(rs2.getDate("time_to").compareTo(now) > 0) {
								String ssnnow = rs2.getString("ssn");
								subquery = "update Departments_Managers set time_to=" + (new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS")).format(now) + " where ssn='" + ssnnow + "' and did=" + did;
								st.executeUpdate(subquery);
							}
						}
						subquery = "insert into Departments_Managers (time_from, time_to, did, ssn) values ( " + (new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS")).format(now) + ", '2099-12-31 23:59:59', " + did + ", '" + ssn + "')";
						st.executeUpdate(subquery);
						rs2.close();
					}
				}
			}
			else {
				if(i == 1) {
					String subquery = "insert into Departments_Managers (time_from, time_to, did, ssn) values ( " + (new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS")).format(now) + ", '2099-12-31 23:59:59', " + did + ", '" + ssn + "')";
					st.executeUpdate(subquery);
				}
			}
			rs.close();
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void uptDeptManager(String ssn, int did) {
		Statement st = null;
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String strnow = dateFormat.format(now);
		String subquery = "";
		try {
			st = conn.createStatement();
			subquery = "select * from Departments_Managers D where D.did=" + did + " order by time_to desc";
			ResultSet rs2 = st.executeQuery(subquery);
			if(rs2.next()) {
				if(rs2.getDate("time_to").compareTo(now) > 0) {
					String ssnnow = rs2.getString("ssn");
					subquery = "update Departments_Managers set time_to='" + strnow + "' where ssn='" + ssnnow + "' and did=" + did;
					st.executeUpdate(subquery);
				}
			}
			rs2.close();
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
//		return subquery;
	}
	
	public void modDeptManager(String ssn, int did) {
		Statement st = null;
//		Statement st2 = null;
		java.sql.Date now = new java.sql.Date(new java.util.Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String strnow = dateFormat.format(now);
		String subquery = "";
		try {
			st = conn.createStatement();
//			st2 = conn.createStatement();
//			subquery = "select * from Departments_Managers D where D.did=" + did + " order by time_to desc";
//			ResultSet rs2 = st.executeQuery(subquery);
//			if(rs2.next()) {
//				if(rs2.getDate("time_to").compareTo(now) > 0) {
//					String ssnnow = rs2.getString("ssn");
//					subquery = "update Departments_Managers set time_to=" + (new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS")).format(now) + " where ssn='" + ssnnow + "' and did=" + did;
//					st2.executeUpdate(subquery);
//				}
//			}
			subquery = "insert into Departments_Managers (time_from, time_to, did, ssn) values ( '" + strnow + "'), '2099-12-31 23:59:59', " + did + ", '" + ssn + "')";
			st.executeUpdate(subquery);
//			rs2.close();
//			st2.close();
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public Departments getSp(int did) {
		Statement st = null;
		Departments dept = new Departments();
		try {
			String query = "select D.name, D.budget, D.isdel from Departments D where D.did=" + did;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				dept.setDid(did);
				dept.setBudget(rs.getInt("budget"));
				dept.setName(rs.getString("name"));
			}
			rs.close();
			st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
		return dept;
	}
	
	public void delDept(int id) {
		Statement st = null;
		try {
			String query = "update Departments set isdel=1 where did=" + id;
			st = conn.createStatement();
		    st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void addDept(Departments d) {
		Statement st = null;
		try {
			String name = d.getName();
			double bud = d.getBudget();
			int isdel = 0;
			st = conn.createStatement();
			String query = "insert into Departments (name, budget, isdel) values ('" + name + "', " + bud + ", " + isdel + ")";
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public void modDept(Departments d, int id) {
		Statement st = null;
		try {
			String name = d.getName();
			double bud = d.getBudget();
			st = conn.createStatement();
//			String query = "insert into Departments (name, budget, isdel) values ('" + name + "', " + bud + ", " + isdel + ")";
			String query = "update Departments set name='" + name + "', budget=" + bud + " where did=" + id;
			st.executeUpdate(query);
		    st.close();
		}
		catch(SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
