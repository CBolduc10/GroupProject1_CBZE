package business.facade;

/**
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc 
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

import business.entities.ItemList;
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
 */
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private Catalog catalog = new Catalog();
	private MemberList members = new MemberList();
	private OrderList orders = new OrderList();
	private static Store store;

	/**
	 * A collection class in Store that stores and handles/processes Order
	 * objects.
	 */
	private class OrderList extends ItemList<Order, String>
			implements Iterable<Order>, Serializable {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * The collection class in Store for Product objects.
	 *
	 */
	private class Catalog extends ItemList<Product, String>
			implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Product> products = new LinkedList<Product>();

		/**
		 * Checks whether a product with a given name exists.
		 * 
		 * @param name the name of the product
		 * @return true iff the product exists
		 * 
		 */
		public boolean searchNames(String name) {
			for (Iterator<Product> iterator = products.iterator(); iterator
					.hasNext();) {
				Product product = (Product) iterator.next();
				if (product.getName().equals(name)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * The collection class for Member objects
	 *
	 */
	private class MemberList extends ItemList<Member, String>
			implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Private for the singleton pattern Creates the order, catalog, and member
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
				request.getMemberFeePaid());
		if (members.insert(member)) {
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

	/**
	 * Organizes the operations for adding a product
	 * 
	 * @param name   product name
	 * @param author product id
	 * @param id     product reorder level
	 * @param price  product price
	 * @return the Product object created and an order for twice the reorder
	 *         level of the product
	 */
	public Result addProduct(Request request) {
		Result result = new Result();
		Product product = new Product(request.getProductName(),
				request.getProductId(),
				Integer.parseInt(request.getProductStock()),
				Integer.parseInt(request.getProductReorderLevel()),
				Double.parseDouble(request.getProductPrice()));
		if (catalog.searchNames(request.getProductName())) {
			result.setResultCode(Result.DUPLICATE_ID);
			return result;
		}
		if (catalog.insert(product)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setProductFields(product);
			Order order = new Order(product.getId(), product.getName(),
					product.getReorderLevel());
			orders.insert(order);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Method for a member to purchase products from the store, grouped as a
	 * transaction. Member enter id and if valid the cashier begins checking out
	 * products. These transaction items alter the stock of products and trigger
	 * reordering when applicable. The product name, item quantity, product
	 * price, price per quantity and transaction total are displayed per entry
	 * of valid item and amount.
	 * 
	 * @param (via request) member id, product id, product reorder level, item
	 *             quantity.
	 * @return result code, (order quantity, order id), product fields, item
	 *         quantity, item total, transaction total.
	 */
	public Result purchaseProducts(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		result.setMemberFields(member);
		Product product = catalog.search(request.getProductId());
		if (product == null) {
			result.setResultCode(Result.NO_SUCH_PRODUCT);
			return result;
		}
		result.setProductFields(product);
		int quantity = Integer.parseInt(request.getItemQuantity());
		Transaction transaction = member.getCurrentTransaction();
		if (product.checkStock(quantity) == false) {
			result.setResultCode(Result.OPERATION_FAILED);
			return result;
		} else {
			TransactionItem item = new TransactionItem(product, quantity);
			transaction.addItem(item);
			product.setStock(product.getStock() - item.getQuantity());
			result.setItemQuantity(String.valueOf(item.getQuantity()));
			result.setItemTotal(String.valueOf(item.getTotal()));
			result.setTransactionTotal(String.valueOf(transaction.getTotal()));
			if (product.checkReorder()) {
				Order order = new Order(product.getId(), product.getName(),
						product.getReorderLevel());
				orders.insert(order);
				result.setOrderQuantity(String.valueOf(order.getQuantity()));
				result.setOrderId(order.getId());
				result.setResultCode(Result.ORDER_PLACED);
				return result;
			}
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
	}

	/**
	 * Checks if a member's current transaction is empty and if so, removes the
	 * transaction.
	 * 
	 * @param (via request) memberId id of member
	 * @return result
	 */
	public Result checkTransaction(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member.getCurrentTransaction().isEmpty()) {
			member.removeCurrentTransaction();
			result.setResultCode(Result.TRANSACTION_EMPTY);
			return result;
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setTransactionTotal(
				String.valueOf(member.getCurrentTransaction().getTotal()));
		return result;
	}

	/**
	 * Creates a transaction for a specific member.
	 * 
	 * @param (via request) member id
	 * @return result
	 */
	public Result createTransaction(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		if (member.addTransaction(new Transaction())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;

	}

	/**
	 * Deletes a transaction for a specific member.
	 * 
	 * @param (via request) member id
	 */
	public void deleteTransaction(Request request) {
		Member member = members.search(request.getMemberId());
		member.removeCurrentTransaction();
	}

	/**
	 * Handles the processing of a transaction for a specific member by
	 * calculating total and change owed upon payment.
	 * 
	 * @param (via request) member id, payment from customer (transactionChange)
	 * @return result transactionChange via processing transaction
	 */
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
		if (transaction.processTransaction()) {
			result.setTransactionChange(String.valueOf(Math
					.abs(transaction.getPayment() - transaction.getTotal())));
			result.setResultCode(Result.TRANSACTION_COMPLETE);
			return result;
		} else {
			result.setResultCode(Result.INSUFFICIENT_FUNDS);
			return result;
		}
	}

	/**
	 * Processes shipments and by fulfilling orders and updating the applicable
	 * product stocks.
	 * 
	 * @param (via request) order id
	 * @return result
	 */
	public Result processShipments(Request request) {
		Result result = new Result();
		Order order = orders.search(request.getOrderId());
		if (order == null) {
			result.setResultCode(Result.NO_ORDER_FOUND);
		} else {
			Product product = catalog.search(order.getProductId());
			int quantity = order.getQuantity();
			product.setStock(product.getStock() + quantity);
			if (!orders.remove(order.getId())) {
				result.setResultCode(Result.OPERATION_FAILED);
				return result;
			}
			result.setProductFields(product);
			result.setResultCode(Result.OPERATION_COMPLETED);
		}
		return result;
	}

	/**
	 * Method to change the price for a product by searching catalog collection.
	 * Returns result to UI.
	 * 
	 * @param (via request) product id
	 * @return result
	 */
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
	 * Searches for a given product
	 * 
	 * @param productId id of the product
	 * @return true iff the product is in the catalog collection
	 */
	public Result searchCatalog(Request request) {
		Result result = new Result();
		Product product = catalog.search(request.getProductId());
		if (product == null) {
			result.setResultCode(Result.NO_SUCH_PRODUCT);
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setProductFields(product);
		}
		return result;
	}

	/**
	 * Returns an iterator to the transactions for a specific member between two
	 * dates
	 * 
	 * @param (via request) memberId member id
	 * @param (via request) startDate starting date for period
	 * @param (via request) endDate ending date for period
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
	 * String form of the store
	 * 
	 */
	@Override
	public String toString() {
		return catalog + "\n" + members;
	}
}
