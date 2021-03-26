package business.entities;

import java.io.Serializable;

/**
 * 
 * @author colin
 *
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String productId;
	private int quantity;
	
	public Order(String id, String productId, int quantity) {
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
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
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		return "Order [id=" + id + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
	
	
}
