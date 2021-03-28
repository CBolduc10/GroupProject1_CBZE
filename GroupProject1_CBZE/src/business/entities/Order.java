package business.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * @author colin
 *
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String ORDER_STRING = "O";
	private static int idCounter;
	private String id;
	private String productId;
	private String productName;
	private int quantity;
	private Calendar date;

	public Order(String productId, String productName, int quantity) {
		this.id = ORDER_STRING + ++idCounter;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.date = new GregorianCalendar();
	}

	public String getId() {
		return id;
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return ((this.date.get(Calendar.MONTH) + 1) + "/"
				+ this.date.get(Calendar.DATE) + "/"
				+ this.date.get(Calendar.YEAR));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productId=" + productId + ", quantity="
				+ quantity + ", date =" + getDate() + "]";
	}

}
