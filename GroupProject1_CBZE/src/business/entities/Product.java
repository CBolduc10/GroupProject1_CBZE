package business.entities;

import java.io.Serializable;

/**
 * Product represents a product available.
 * 
 * @author Zachary Boling-Green, Ethan Nunn, Brian Le and Colin Bolduc
 *
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private int stock;
	private double price;
	private int reorderLevel;

	/**
	 * Creates a product with a given name, id, reorder level, price and stock
	 * while initializing stock to 0;
	 * 
	 * @param name         Product Name
	 * @param id           Product ID
	 * @param reorderLevel Reorder Level for the product
	 * @param price        Price of the Product
	 */
	public Product(String name, String id, int reorderLevel, double price) {
		this.name = name;
		this.id = id;
		this.stock = 0;
		this.reorderLevel = reorderLevel;
		this.price = price;
	}

	public void reorderLevel() {
		if (this.stock <= this.reorderLevel) {

		}
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public int getStock() {
		return stock;
	}

	public double getPrice() {
		return price;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean setPrice(double price) {
		this.price = price;
		return true;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Product other = (Product) object;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Product Name: " + name + "|| Product ID: " + id
				+ " || Reorder Level: " + reorderLevel + "  || Qty: " + stock
				+ " || MSRP:" + price;
	}
}