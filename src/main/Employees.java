package main;
/**
 * 
 * Class of Employees
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
import java.sql.*;

public class Employees {
	
	public String ssn;
	public String name;
	public String auth;
	public String depart;
	public Date since;
	public String pw;
	public int isdel;
	public int prodnum;
	public int aid;
	public int did;
	
	public Employees(){}
	
	public Employees(String ssn, String name, String auth, String depart, Date since, String pw) {
		this.ssn = ssn;
		this.name = name;
		this.auth = auth;
		this.depart = depart;
		this.since = since;
		this.pw = pw;
		isdel = 0;
	}
	
	public Employees(String ssn, String name, String auth, String depart, Date since, String pw, int isdel) {
		this.ssn = ssn;
		this.name = name;
		this.auth = auth;
		this.depart = depart;
		this.since = since;
		this.pw = pw;
		this.isdel = isdel;
	}

	public String getSsn() {
		return ssn;
	}

	public String getName() {
		return name;
	}

	public String getAuth() {
		return auth;
	}

	public String getDepart() {
		return depart;
	}

	public Date getSince() {
		return since;
	}

	public String getPw() {
		return pw;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	
	public int getProdnum() {
		return prodnum;
	}

	public void setProdnum(int prodnum) {
		this.prodnum = prodnum;
	}

	public int getAid() {
		return aid;
	}

	public int getDid() {
		return did;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String toString() {
	    return ssn + ": " + name + ", department: " + depart + ", title: " + auth + ", since: " + since.toString();
	}
	
	public String toHTML() {
		  return "<tr><td>" + ssn + "</td><td>" + name + "</td><td>" + depart + "</td><td>" + since.toString() + "</td><td>" + auth + "</td><td><a href='/PCFactory/ServEmpView?id=" + ssn + "'>View</a> <a href='/PCFactory/ServEmpMod?id=" + ssn + "'>Modify</a> <a href='/PCFactory/ServEmpDel?id=" + ssn + "'>Delete</a></td></tr>";
	}
	
	public String toHTMLRoster() {
		  return "<tr><td>" + ssn + "</td><td>" + name + "</td><td>" + since.toString() + "</td><td>" + auth + "</td><td><a href='/PCFactory/ServEmpView?id=" + ssn + "'>View</a> <a href='/PCFactory/ServEmpDel?id=" + ssn + "'>Delete</a></td></tr>";
	}
}
