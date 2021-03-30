package business.entities;

import java.io.Serializable;

/**
 * Product represents a product available.
 * 
 * @author Zachary Boling-Green, Ethan Nunn, Brian Le and Colin Bolduc
 *
 */
public class Product implements Serializable, Matchable<String> {
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
	public Product(String name, String id, int stock, int reorderLevel,
			double price) {
		this.name = name;
		this.id = id;
		this.stock = stock;
		this.reorderLevel = reorderLevel;
		this.price = price;
	}

	/**
	 * Method to check if a product's stock has been depleted to the reorder
	 * level or below.
	 * 
	 * @return true or false iff stock is less than or equal to reorder level.
	 */
	public boolean checkReorder() {
		if (this.stock <= this.reorderLevel) {
			return true;
		}
		return false;
	}

	/**
	 * Getter for product's name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for product's id.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Getter for product's stock.
	 * 
	 * @return stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Getter for product's price.
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Getter for product's reorder level.
	 * 
	 * @return reorderLevel
	 */
	public int getReorderLevel() {
		return reorderLevel;
	}

	/**
	 * Setter for product's name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for product's id.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter for product's stock (ensures stock cannot be less than 0 after
	 * update).
	 * 
	 * @param stock
	 * @return true or false if updated successfully
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Checks the availability of product's stock (ensures stock cannot be less
	 * than 0).
	 * 
	 * @param stock
	 * @return true or false if updated successfully
	 */
	public boolean checkStock(int quantity) {
		if (this.stock - quantity >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Setter for product price.
	 * 
	 * @param price
	 * @return true or false if successful
	 */
	public boolean setPrice(double price) {
		this.price = price;
		return true;
	}

	/**
	 * Setter for product's reorder level.
	 * 
	 * @param reorderLevel
	 */
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

	/**
	 * Checks whether the product is equal to the one supplied
	 * 
	 * @param object the product that should be compared
	 * @return true iff the product ids match
	 */
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

	/**
	 * String form of the product
	 * 
	 */
	@Override
	public String toString() {
		return "Product Name: " + name + "|| Product ID: " + id
				+ " || Reorder Level: " + reorderLevel + "  || Qty: " + stock
				+ " || MSRP:" + price;
	}

	@Override
	public boolean matches(String id) {
		return this.id.equals(id);
	}
}