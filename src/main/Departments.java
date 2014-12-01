package main;
/**
 * 
 * Class of Departments
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */

public class Departments {
	public int did;
	public String name;
	public double budget;
	public int isdel;
	public String manager;
	public int empnum;

	public Departments(){}
	
	public Departments(int id, String name) {
		this.did = id;
		this.name = name;
		budget = 0.0;
		isdel = 0;
	}
	
	public Departments(int id, String name, double budget) {
		this.did = id;
		this.name = name;
		this.budget = budget;
		isdel = 0;
	}
	
	public Departments(int id, String name, double budget, int isdel) {
		this.did = id;
		this.name = name;
		this.budget = budget;
		this.isdel = isdel;
	}

	public int getDid() {
		return did;
	}

	public String getName() {
		return name;
	}

	public double getBudget() {
		return budget;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}
	
	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public String toString() {
	    return did + ": " + name + ", budget: " + budget;
	}
	
	public String toHTML() {
//		  return "<tr><td>" + did + "</td><td>" + name + "</td><td>" + budget + "</td></tr>";
		return "<tr><td>" + name + "</td><td>" + budget + "</td><td><a href='/PCFactory/ServDeptView?id=" + did + "'>View</a> <a href='/PCFactory/ServDeptMod?id=" + did + "'>Modify</a> <a href='/PCFactory/ServDeptDel?id=" + did + "'>Delete</a></td></tr>";
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public int getEmpnum() {
		return empnum;
	}

	public void setEmpnum(int empnum) {
		this.empnum = empnum;
	}
	
}
