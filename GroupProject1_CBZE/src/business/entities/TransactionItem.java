package business.entities;

public class TransactionItem {
	private String name;
	private String id;
	private int quantity;
	private double price;
	private double total;

	public TransactionItem(Product product, int quantity) {
		this.name = product.getName();
		this.id = product.getId();
		this.quantity = quantity;
		this.price = product.getPrice();
		this.total = price * quantity;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public double getTotal() {
		return total;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "TransactionItem [Product = " + name + ", Product ID = " + id
				+ ", quantity = " + quantity + ", price = " + price
				+ ", total = " + total + "]";
	}

}
