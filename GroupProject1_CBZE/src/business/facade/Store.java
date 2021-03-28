package business.facade;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.Member;
import business.entities.Order;
import business.entities.Product;
import business.entities.Transaction;
import business.entities.TransactionItem;
import business.entities.iterators.SafeMemberIterator;
import business.entities.iterators.SafeOrderIterator;
import business.entities.iterators.SafeProductIterator;

/**
 * The facade class handling all requests from users.
 * 
 * @author Brahma Dathan
 *
 */
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private Catalog catalog = new Catalog();
	private MemberList members = new MemberList();
	private OrderList orders = new OrderList();
	private static Store store;

	/**
	 * 
	 * @author Brahma Dathan and Sarnath Ramnath
	 * @Copyright (c) 2010
	 * 
	 *            Redistribution and use with or without modification, are
	 *            permitted provided that the following conditions are met:
	 *
	 *            - the use is for academic purpose only - Redistributions of
	 *            source code must retain the above copyright notice, this list
	 *            of conditions and the following disclaimer. - Neither the name
	 *            of Brahma Dathan or Sarnath Ramnath may be used to endorse or
	 *            promote products derived from this software without specific
	 *            prior written permission.
	 *
	 *            The authors do not make any claims regarding the correctness
	 *            of the code in this module and are not responsible for any
	 *            loss or damage resulting from its use.
	 */

	private class OrderList implements Iterable<Order>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Order> orders = new LinkedList<Order>();

		/**
		 * Checks whether a product with a given book id exists.
		 * 
		 * @param productId the id of the product
		 * @return true iff the book exists
		 * 
		 */
		public Order search(String orderId) {
			for (Iterator<Order> iterator = orders.iterator(); iterator
					.hasNext();) {
				Order order = (Order) iterator.next();
				if (order.getId().equals(orderId)) {
					return order;
				}
			}
			return null;
		}

		/**
		 * Inserts a product into the collection
		 * 
		 * @param product the product to be inserted
		 * @return true iff the product could be inserted. Currently always true
		 */
		public boolean insertOrder(Order order) {// insertBook
			orders.add(order);
			return true;
		}

		/**
		 * Returns an iterator to all books
		 * 
		 * @return iterator to the collection
		 */
		public Iterator<Order> iterator() {
			return orders.iterator();
		}

		public boolean remove(Order order) {
			return orders.remove(order);
		}

		/**
		 * String form of the collection
		 * 
		 */
		public String toString() {
			return orders.toString();
		}
	}

	/**
	 * The collection class for Product objects
	 * 
	 * @author Brahma Dathan and Sarnath Ramnath
	 *
	 */
	private class Catalog implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Product> products = new LinkedList<Product>();

		/**
		 * Checks whether a product with a given book id exists.
		 * 
		 * @param productId the id of the product
		 * @return true iff the book exists
		 * 
		 */
		public Product search(String productId) {
			for (Iterator<Product> iterator = products.iterator(); iterator
					.hasNext();) {
				Product product = (Product) iterator.next();
				if (product.getId().equals(productId)) {
					return product;
				}
			}
			return null;
		}

		/**
		 * Inserts a product into the collection
		 * 
		 * @param product the product to be inserted
		 * @return true iff the product could be inserted. Currently always true
		 */
		public boolean insertProduct(Product product) {// insertBook
			products.add(product);
			return true;
		}

		/**
		 * Returns an iterator to all books
		 * 
		 * @return iterator to the collection
		 */
		public Iterator<Product> iterator() {
			return products.iterator();
		}

		/**
		 * String form of the collection
		 * 
		 */
		public String toString() {
			return products.toString();
		}
	}

	/**
	 * The collection class for Member objects
	 * 
	 * @author Brahma Dathan and Sarnath Ramnath
	 *
	 */
	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		/**
		 * Checks whether a member with a given member id exists.
		 * 
		 * @param memberId the id of the member
		 * @return true iff member exists
		 * 
		 */
		public Member search(String memberId) {
			for (Iterator<Member> iterator = members.iterator(); iterator
					.hasNext();) {
				Member member = iterator.next();
				if (member.getId().equals(memberId)) {
					return member;
				}
			}
			return null;
		}

		/**
		 * Inserts a member into the collection
		 * 
		 * @param member the member to be inserted
		 * @return true iff the member could be inserted. Currently always true
		 */
		public boolean insertMember(Member member) {
			members.add(member);
			return true;
		}

		public Iterator<Member> iterator() {
			return members.iterator();
		}

		/**
		 * String form of the collection
		 * 
		 */
		@Override
		public String toString() {
			return members.toString();
		}

		public boolean remove(String memberId) {
			Member member = search(memberId);
			if (member == null) {
				return false;
			} else {
				return members.remove(member);
			}
		}
	}

	/**
	 * Private for the singleton pattern Creates the catalog and member
	 * collection objects
	 */
	private Store() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static Store instance() {
		if (store == null) {
			return store = new Store();
		} else {
			return store;
		}
	}

	/**
	 * Organizes the operations for adding a product
	 * 
	 * @param title  product title
	 * @param author product id
	 * @param id     product reorder level
	 * @param price  product price
	 * @return the Product object created
	 */
	public Result addProduct(Request request) {// addBook
		Result result = new Result();
		Product product = new Product(request.getProductName(),
				request.getProductId(),
				Integer.parseInt(request.getProductStock()),
				Integer.parseInt(request.getProductReorderLevel()),
				Double.parseDouble(request.getProductPrice()));
		if (catalog.insertProduct(product)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setProductFields(product);
			Order order = new Order(product.getId(), product.getName(),
					product.getReorderLevel() * 2);
			orders.insertOrder(order);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Organizes the operations for adding a member
	 * 
	 * @param name    member name
	 * @param address member address
	 * @param phone   member phone
	 * @param feePaid member fee paid
	 * @return the Member object created
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(),
				request.getMemberAddress(), request.getMemberPhone(),
				Double.parseDouble(request.getMemberFeePaid()));
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Removes a specific Member from the MemberList
	 * 
	 * @param memberId id of the member
	 * @return a code representing the outcome
	 */
	public Result removeMember(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		if (members.remove(request.getMemberId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Result changePrice(Request request) {
		Result result = new Result();
		Product product = catalog.search(request.getProductId());
		if (product == null) {
			result.setResultCode(Result.NO_SUCH_PRODUCT);
			return result;
		}
		if (product.setPrice(Double.parseDouble(request.getProductPrice()))) {
			result.setProductName(product.getName());
			result.setProductPrice(String.valueOf((product.getPrice())));
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Result purchaseProducts(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		Product product = catalog.search(request.getProductId());
		if (product == null) {
			result.setResultCode(Result.NO_SUCH_PRODUCT);
			return result;
		}
		Transaction transaction = member.getCurrentTransaction();
		TransactionItem item = new TransactionItem(product,
				Integer.parseInt(request.getItemQuantity()));
		if (!transaction.addItem(item) || product.setStock(product.getStock()
				- Integer.parseInt(request.getItemQuantity())) == false) {
			result.setResultCode(Result.OPERATION_FAILED);
		} else {
			result.setProductFields(product);
			if (product.checkReorder()) {
				System.out.println("The current quantity of "
						+ product.getName() + " is low");
				System.out.println("A new order for " + product.getName()
						+ " has been placed");
				Order order = new Order(product.getId(), product.getName(),
						product.getReorderLevel() * 2);
				result.setOrderQuantity(String.valueOf(order.getQuantity()));
				result.setOrderId(order.getId());
				System.out.println("Amount ordered: " + order.getQuantity());
				System.out.println("Order ID: " + order.getId());
			}
			product.setStock(product.getStock()
					- Integer.parseInt(request.getItemQuantity()));
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setItemQuantity(String.valueOf(item.getQuantity()));
			result.setItemTotal(String.valueOf(item.getTotal()));
			result.setTransactionTotal(String.valueOf(transaction.getTotal()));
		}
		return result;
	}

	public Result processShipments(Request request) {
		Result result = new Result();
		Order order = orders.search(request.getOrderId());
		if (order == null) {
			result.setResultCode(Result.NO_ORDER_FOUND);
		} else {
			Product product = catalog.search(order.getProductId());
			int quantity = order.getQuantity();
			product.setStock(product.getStock() + quantity);
			if (!orders.remove(order)) {
				result.setResultCode(Result.OPERATION_FAILED);
				return result;
			}
			result.setProductFields(product);
			result.setResultCode(Result.OPERATION_COMPLETED);
		}
		return result;
	}

	public void createTransaction(Request request) {
		Member member = members.search(request.getMemberId());
		member.addTransaction(new Transaction());
	}

	/**
	 * Searches for a given member
	 * 
	 * @param memberId id of the member
	 * @return true iff the member is in the member list collection
	 */
	public Result searchMembership(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
		}
		return result;
	}

	/**
	 * Returns an iterator to the transactions for a specific member on a
	 * certain date
	 * 
	 * @param memberId member id
	 * @param date     date of issue
	 * @return iterator to the collection
	 */
	public Iterator<Transaction> getTransactions(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null) {
			return new LinkedList<Transaction>().iterator();
		}
		return member.getTransactionsBetweenDates(request.getStartDate(),
				request.getEndDate());
	}

	/**
	 * Retrieves a deserialized version of the store from disk
	 * 
	 * @return a Store object
	 */
	public static Store retrieve() {
		try {
			FileInputStream file = new FileInputStream("StoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			store = (Store) input.readObject();
			Member.retrieve(input);
			return store;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Store object
	 * 
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(store);
			Member.save(output);
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns an iterator to Member info. The Iterator returned is a safe one,
	 * in the sense that only copies of the Member fields are assembled into the
	 * objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Member fields are valid.
	 */
	public Iterator<Result> getMembers() {
		return new SafeMemberIterator(members.iterator());
	}

	/**
	 * Returns an iterator to Product info. The Iterator returned is a safe one,
	 * in the sense that only copies of the Product fields are assembled into
	 * the objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Product fields are valid.
	 */
	public Iterator<Result> getProducts() {
		return new SafeProductIterator(catalog.iterator());
	}

	/**
	 * Returns an iterator to Order info. The Iterator returned is a safe one,
	 * in the sense that only copies of the Order fields are assembled into the
	 * objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Product fields are valid.
	 */
	public Iterator<Result> getOrders() {
		return new SafeOrderIterator(orders.iterator());
	}

	/**
	 * String form of the library
	 * 
	 */
	@Override
	public String toString() {
		return catalog + "\n" + members;
	}

	public Result getChange(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		Transaction transaction = member.getCurrentTransaction();
		transaction
				.setPayment(Double.parseDouble(request.getTransactionChange()));
		result.setTransactionChange(
				String.valueOf(transaction.processTransaction()));

		return result;
	}
}
