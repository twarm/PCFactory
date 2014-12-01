package items;
/**
 * 
 * Class of Products made of Components
 * 
 * @author Jianyu Wang(jw3236@drexel.edu)
 * 
 */
public class ProductMakeOf {
	public String product;
	public String component;
	
	public ProductMakeOf(){}
	
	public ProductMakeOf(String product, String component) {
		this.product = product;
		this.component = component;
	}

	public String getProduct() {
		return product;
	}

	public String getComponent() {
		return component;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setComponent(String component) {
		this.component = component;
	}
	
	public String toString() {
	    return "Product: " + product + ", component: " + component;
	}
	
	public String toHTML() {
		return "<tr><td>" + product + "</td><td>" + component + "</td></tr>";
	}
}
