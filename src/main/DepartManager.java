package main;
/**
 * 
 * Class of managers of departments
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
import java.sql.*;
import java.util.Date;
public class DepartManager {
	public Date from;
	public Date to;
	public String emp;
	public String depart;
	public String ssn;
	
	public DepartManager(){}
	
	public DepartManager(Date from, Date to, String emp, String depart) {
		this.from = from;
		this.to = to;
		this.emp = emp;
		this.depart = depart;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public String getEmp() {
		return emp;
	}

	public String getDepart() {
		return depart;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String toString() {
	    return "From: " + from.toString() + ", to: " + to.toString() + ", employee: " + emp + ", department: " + depart;
	}
	
	public String toHTML() {
		String timeto;
		Date now = new Date();
		if(to.compareTo(now) > 0) {
			timeto = "Now";
		}
		else {
			timeto = to.toString();
		}
		return "<tr><td>" + from.toString() + "</td><td>" + timeto + "</td><td>" + ssn + "</td><td>" + emp + "</td></tr>";
	}
}
