package items;
/**
 * 
 * Class of Sales Records
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
import java.sql.*;
public class SalesRecords {
	public int sid;
	public Date datetime;
	public int num;
	public double price;
	public int isdel;
	public String dealer;
	public String prod;
	public double totalprice;
	public int pid;
	public int dealerid;
	
	public SalesRecords(){}
	
	public SalesRecords(int sid, Date datetime, int num, double price) {
		this.sid = sid;
		this.datetime = datetime;
		this.num = num;
		this.price = price;
		isdel = 0;
	}
	
	public SalesRecords(int sid, Date datetime, int num, double price, int isdel) {
		this.sid = sid;
		this.datetime = datetime;
		this.num = num;
		this.price = price;
		this.isdel = isdel;
	}

	public int getSid() {
		return sid;
	}

	public Date getDatetime() {
		return datetime;
	}

	public int getNum() {
		return num;
	}

	public double getPrice() {
		return price;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	
	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getDealerid() {
		return dealerid;
	}

	public void setDealerid(int dealerid) {
		this.dealerid = dealerid;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public String toString() {
	    return "Date: " + datetime + ", number: " + num + ", price: " + price;
	}
	
	public String toHTML() {
		return "<tr><td>" + sid + "</td><td>" + datetime.toString() + "</td><td>" + num + "</td><td>" + price + "</td><td><a href='/PCFactory/ServSalerdView?id=" + sid + "'>View</a> <a href='/PCFactory/ServSalerdMod?id=" + sid + "'>Modify</a> <a href='/PCFactory/ServSalerdDel?id=" + sid + "'>Delete</a></td></tr>";
	}
	
	public String toStatHTML() {
		return "<tr><td>" + sid + "</td><td>" + datetime.toString() + "</td><td>" + num + "</td><td>" + price + "</td><td>" + totalprice + "</td><td><a href='/PCFactory/ServStatView?id=" + sid + "'>View</a></tr>";
	}
}
