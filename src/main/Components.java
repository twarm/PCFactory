package main;
/**
 * 
 * Class of Components
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
public class Components {
	public int cid;
	public String name;
	public int stock;
	public double price;
	public int isdel;
	public String loca;
	public int pnum;
	public int lid;
	
	public Components(){}
	
	public Components(int cid, String name, int stock, double price) {
		this.cid = cid;
		this.name = name;
		this.stock = stock;
		this.price = price;
		isdel = 0;
	}
	
	public Components(int cid, String name, int stock, double price, int isdel) {
		this.cid = cid;
		this.name = name;
		this.stock = stock;
		this.price = price;
		this.isdel = isdel;
	}

	public int getCid() {
		return cid;
	}

	public String getName() {
		return name;
	}

	public int getStock() {
		return stock;
	}

	public double getPrice() {
		return price;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	
	public String getLoca() {
		return loca;
	}

	public void setLoca(String loca) {
		this.loca = loca;
	}

	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String toString() {
	    return "Name: " + name + ", stock: " + stock + ", price: " + price;
	}
	
	public String toHTML() {
		return "<tr><td>" + cid + "</td><td>" + name + "</td><td>" + stock + "</td><td>" + price + "</td><td>" + loca + "</td><td><a href='/PCFactory/ServCompView?id=" + cid + "'>View</a> <a href='/PCFactory/ServCompMod?id=" + cid + "'>Modify</a> <a href='/PCFactory/ServCompDel?id=" + cid + "'>Delete</a></td></tr>";
	}
}
