package items;
/**
 * 
 * Class of Dealers
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
public class Dealers {
	public int dealerid;
	public String name;
	public String phone;
	public int isdel;
	public int snum;
	
	public Dealers(){}
	
	public Dealers(int dealerid, String name, String phone) {
		this.dealerid = dealerid;
		this.name = name;
		this.phone = phone;
		this.isdel = 0;
	}
	
	public Dealers(int dealerid, String name, String phone, int isdel) {
		this.dealerid = dealerid;
		this.name = name;
		this.phone = phone;
		this.isdel = isdel;
	}

	public int getDealerid() {
		return dealerid;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setDealerid(int dealerid) {
		this.dealerid = dealerid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	
	public String toString() {
	    return "Name: " + name + ", phone: " + phone;
	}
	
	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public String toHTML() {
		return "<tr><td>" + dealerid + "</td><td>" + name + "</td><td>" + phone + "</td><td><a href='/PCFactory/ServDealerView?id=" + dealerid + "'>View</a> <a href='/PCFactory/ServDealerMod?id=" + dealerid + "'>Modify</a> <a href='/PCFactory/ServDealerDel?id=" + dealerid + "'>Delete</a></td></tr>";
	}
}
