package business.entities;

/**
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import java.io.Serializable;

/**
 * Represents an product and quantity itemized within a transaction.
 *
 */

public class TransactionItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private int quantity;
	private double price;
	private double total;

	/**
	 * Creates a TransactionItem by getting the name, id and price from an input
	 * product and coupling it with a specific quantity (also input) also gets a
	 * total for the itemization.
	 * 
	 * @param product  product corresponding to TransactionItem
	 * @param quantity quantity of items that comprise TransactionItem
	 */
	public TransactionItem(Product product, int quantity) {
		this.name = product.getName();
		this.id = product.getId();
		this.quantity = quantity;
		this.price = product.getPrice();
		this.total = price * quantity;
	}

	/**
	 * Getter for TransactionItem name.
	 * 
	 * @return name product's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for corresponding product id.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Getter for quantity of product that comprise the TransactionItem.
	 * 
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Getter for price of product corresponding with TransactionItem
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Getter for TransactionItem's total value.
	 * 
	 * @return total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Setter for the TransactionItem name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for the product id corresponding to TransactionItem.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter for the quantity of products corresponding to TransactionItem.
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Setter for TransactionItem's price corresponding to product.
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Setter for TransactionItem's total.
	 * 
	 * @param total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * String form of the TransactionItem
	 * 
	 */
	@Override
	public String toString() {
		return "TransactionItem [Product = " + name + ", Product ID = " + id
				+ ", quantity = " + quantity + ", price = " + price
				+ ", total = " + total + "]";
	}

}
