package main;
/**
 * 
 * Class of Products
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
public class Products {
	public int pid;
	public String name;
	public int stock;
	public int isdel;
	public int compnum;
	public int empnum;
	public String ename;
	public int snum;
	public String ssn;
	
	public Products(){}
	
	public Products(int pid, String name, int stock) {
		this.pid = pid;
		this.name = name;
		this.stock = stock;
		isdel = 0;
	}
	
	public Products(int pid, String name, int stock, int isdel) {
		this.pid = pid;
		this.name = name;
		this.stock = stock;
		this.isdel = isdel;
	}

	public int getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public int getStock() {
		return stock;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	
	public int getCompnum() {
		return compnum;
	}

	public int getEmpnum() {
		return empnum;
	}

	public void setCompnum(int compnum) {
		this.compnum = compnum;
	}

	public void setEmpnum(int empnum) {
		this.empnum = empnum;
	}
	
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String toString() {
	    return "name: " + name + ", stock: " + stock;
	}
	
	public String toHTML() {
		return "<tr><td>" + pid + "</td><td>" + name + "</td><td>" + stock + "</td><td><a href='/PCFactory/ServProdView?id=" + pid + "'>View</a> <a href='/PCFactory/ServProdMod?id=" + pid + "'>Modify</a> <a href='/PCFactory/ServProdDel?id=" + pid + "'>Delete</a></td></tr>";
	}
}
