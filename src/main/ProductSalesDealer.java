package main;
/**
 * 
 * Class of relationship among Products, SaleRecords and Dealers 
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
import java.sql.Date;

public class ProductSalesDealer {
	public String product;
	public Date datetime;
	public String dealer;
	
	public ProductSalesDealer(){}
	
	public ProductSalesDealer(String product, Date datetime, String dealer) {
		this.product = product;
		this.datetime = datetime;
		this.dealer = dealer;
	}

	public String getProduct() {
		return product;
	}

	public Date getDatetime() {
		return datetime;
	}

	public String getDealer() {
		return dealer;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	
	public String toString() {
	    return "Product: " + product + ", date: " + datetime + ", dealer: " + dealer;
	}
	
	public String toHTML() {
		return "<tr><td>" + product + "</td><td>" + datetime + "</td><td>" + dealer + "</td></tr>";
	}
}
