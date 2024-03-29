package business.facade;

/**
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import business.entities.Member;
import business.entities.Order;
import business.entities.Product;

/**
 * The DataTransfer class is used to facilitate data transfer between Store and
 * UserInterface. It is also used to support iterating over Member, Product,
 * TransactionItem and Order objects. The class stores copies of fields that may
 * be sent in either direction.
 *
 */
public abstract class DataTransfer {
	private String productId;
	private String productName;
	private String productStock;
	private String productPrice;
	private String productReorderLevel;
	private String itemQuantity;
	private String itemTotal;
	private String transactionTotal;
	private String transactionChange;
	private String memberId;
	private String memberName;
	private String memberAddress;
	private String memberPhone;
	private String memberDateJoined;
	private String memberFeePaid;
	private String orderId;
	private String orderProductId;
	private String orderProductName;
	private String orderQuantity;
	private String orderDate;

	/**
	 * This sets all fields to "none".
	 */
	public DataTransfer() {
		reset();
	}

	/*
	 * Getters and setters for all fields
	 */

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductStock() {
		return productStock;
	}

	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductReorderLevel() {
		return productReorderLevel;
	}

	public void setProductReorderLevel(String productReorderLevel) {
		this.productReorderLevel = productReorderLevel;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberFeePaid() {
		return memberFeePaid;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberDateJoined() {
		return memberDateJoined;
	}

	public void setMemberDateJoined(String memberDateJoined) {
		this.memberDateJoined = memberDateJoined;
	}

	public void setMemberFeePaid(String memberFeePaid) {
		this.memberFeePaid = memberFeePaid;
	}

	public String getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(String itemTotal) {
		this.itemTotal = itemTotal;
	}

	public String getTransactionTotal() {
		return transactionTotal;
	}

	public void setTransactionTotal(String transactionTotal) {
		this.transactionTotal = transactionTotal;
	}

	public String getTransactionChange() {
		return transactionChange;
	}

	public void setTransactionChange(String transactionChange) {
		this.transactionChange = transactionChange;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}

	public String getOrderProductName() {
		return orderProductName;
	}

	public void setOrderProductName(String orderProductName) {
		this.orderProductName = orderProductName;
	}

	public String getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	/**
	 * Sets all the member-related fields using the Member parameter.
	 * 
	 * @param member the member whose fields should be copied.
	 */
	public void setMemberFields(Member member) {
		memberId = member.getId();
		memberName = member.getName();
		memberPhone = member.getPhone();
		memberAddress = member.getAddress();
		memberDateJoined = member.getDateJoined().toString();
		memberFeePaid = String.valueOf(member.getFeePaid());
	}

	/**
	 * Sets all the Product-related fields using the Product parameter. If the
	 * Product is not issued "none" is stored in the borrower and due date
	 * fields.
	 * 
	 * @param Product the Product whose fields should be copied.
	 */
	public void setProductFields(Product product) {
		productId = product.getId();
		productName = product.getName();
		if (product.getStock() != 0) {
			productStock = String.valueOf(product.getStock());
		} else {
			productStock = "none";
		}
		productPrice = String.valueOf(product.getPrice());
		productReorderLevel = String.valueOf(product.getReorderLevel());
	}

	/**
	 * Sets all the order-related fields using the Order parameter.
	 * 
	 * @param order the order whose fields should be copied.
	 */
	public void setOrderFields(Order order) {
		orderId = order.getId();
		orderProductId = order.getProductId();
		orderProductName = order.getProductName();
		orderQuantity = String.valueOf(order.getQuantity());
		orderDate = order.getDate();
	}

	/**
	 * Sets all String fields to "none"
	 */
	public void reset() {
		productId = "none";
		productName = "none";
		productStock = "none";
		productPrice = "none";
		productReorderLevel = "none";
		itemQuantity = "none";
		itemTotal = "none";
		transactionTotal = "none";
		transactionChange = "none";
		orderId = "none";
		orderQuantity = "none";
		orderProductId = "none";
		orderProductName = "none";
		orderDate = "none";
		memberId = "none";
		memberName = "none";
		memberPhone = "none";
		memberAddress = "none";
		memberDateJoined = "none";
		memberFeePaid = "none";
	}
}
