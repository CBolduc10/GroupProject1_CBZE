package ui;

/**
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 * @Copyright (c) 2010
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

import business.entities.Transaction;
import business.entities.TransactionItem;
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;
import business.tests.AutomatedTester;

/**
 * 
 * This class implements the user interface for the Store project. The commands
 * are encoded as integers using a number of static final variables. A number of
 * utility methods exist to make it easier to parse the input.
 *
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));
	private static Store store;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;// REMOVE MEMBER
	private static final int ADD_PRODUCT = 3;// ADD PRODUCT
	private static final int CHECK_OUT = 4;// CHECK OUT PRODUCTS
	private static final int PROCESS_SHIPMENT = 5;// PROCESS INCOMING SHIPMENT
	private static final int CHANGE_PRICE = 6;// MODIFY PRODUCT PRICE
	private static final int PRODUCT_INFORMATION = 7;// DISPLAY PRODUCT INFO
	private static final int MEMBER_INFORMATION = 8;// DISPLAY MEMBER INFO
	private static final int PRINT_TRANSACTIONS = 9;// PRINT TRANSACTIONS
	private static final int OUTSTANDING_ORDERS = 10;// GET OUTRSTANDING ORDERS
	private static final int GET_MEMBERS = 11; // GET LIST OF ALL MEMBERS
	private static final int GET_PRODUCTS = 12;// GET LIST OF ALL PRODUCTS
	private static final int SAVE = 13; // SAVE DATA
	private static final int HELP = 14; // DISPLAY HELP

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved
	 * data. Otherwise, it gets a singleton Store object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
			retrieve();
		} else {
			store = Store.instance();
			if (yesOrNo("Do you want to generate a test bed and invoke the functionality using asserts?")) {
				AutomatedTester auto = new AutomatedTester();
				auto.testAll();
			}
		}
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Gets a name after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 * 
	 */
	public String getName(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);

	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 * 
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt the string for prompting
	 * @return the integer corresponding to the string
	 * 
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat
						.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 * 
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(
						getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 * 
	 */
	public void help() {
		System.out
				.println("Enter a number between 0 and 12 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(REMOVE_MEMBER + " to remove a member");
		System.out.println(ADD_PRODUCT + " to add a product");
		System.out.println(CHECK_OUT + " to check out member");
		System.out.println(PROCESS_SHIPMENT + " to process a shipment");
		System.out.println(CHANGE_PRICE + " to change product price.");
		System.out.println(
				PRODUCT_INFORMATION + " to retrieve product information");
		System.out.println(
				MEMBER_INFORMATION + " to retrieve member information");
		System.out
				.println(PRINT_TRANSACTIONS + " to print member transactions");
		System.out
				.println(OUTSTANDING_ORDERS + " to display outstanding orders");
		System.out.println(GET_MEMBERS + " to  print all members");
		System.out.println(GET_PRODUCTS + " to  print all products");
		System.out.println(SAVE + " to save data");
		System.out.println(HELP + " for help");
	}

	/**
	 * Method to be called for adding a member. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for adding the
	 * member.
	 * 
	 */
	public void addMember() {
		Request.instance().setMemberName(getName("Enter member name"));
		Request.instance().setMemberAddress(getName("Enter address"));
		Request.instance().setMemberPhone(getName("Enter phone"));
		Request.instance().setMemberFeePaid(getName("Enter fee paid"));
		Result result = store.addMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not add member");
		} else {
			System.out.println(result.getMemberName() + "'s id is "
					+ result.getMemberId());
		}
	}

	/**
	 * Method to be called for removing Members. Prompts the user for the
	 * appropriate value for Id and uses the appropriate Store method for
	 * removing members.
	 * 
	 */
	public void removeMember() {
		do {
			Request.instance().setMemberId(getToken("Enter member id"));
			Result result = store.removeMember(Request.instance());
			switch (result.getResultCode()) {
			case Result.NO_SUCH_MEMBER:
				System.out.println("No such Member with id "
						+ Request.instance().getMemberId() + " in Store");
				break;
			case Result.OPERATION_FAILED:
				System.out.println("Member could not be removed");
				break;
			case Result.OPERATION_COMPLETED:
				System.out.println(" Member has been removed");
				break;
			default:
				System.out.println("An error has occurred");
			}
			if (!yesOrNo("Remove more members?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for adding a product. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for adding the
	 * product.
	 * 
	 */
	public void addProducts() {
		do {
			Request.instance().setProductName(getName("Enter name"));
			Request.instance().setProductId(getToken("Enter id"));
			Request.instance().setProductStock(getToken("Enter stock at hand"));
			Request.instance().setProductPrice(getName("Enter price"));
			Request.instance()
					.setProductReorderLevel(getName("Enter reorder level"));
			Result result = store.addProduct(Request.instance());
			if (result.getResultCode() != Result.OPERATION_COMPLETED) {
				System.out.println("Product could not be added");
			} else {
				System.out.println(
						"Product " + result.getProductName() + " added");
			}
		} while (yesOrNo("Add more products?"));
	}

	/**
	 * Method to be called for checking out products. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for purchasing
	 * and paying for products.
	 * 
	 */
	public void checkOutProducts() {
		Request.instance().setMemberId(getToken("Enter member id"));
		Result result = store.searchMembership(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println(
					"No member with id " + Request.instance().getMemberId());
			return;
		}
		store.createTransaction(Request.instance());
		do {
			Request.instance().setProductId(getToken("Enter product id"));
			Request.instance()
					.setItemQuantity(getName("Enter product quantity"));
			result = store.purchaseProducts(Request.instance());
			if (result.getResultCode() == Result.NO_SUCH_PRODUCT) {
				System.out.println("Product not found");
			}
			if (result.getResultCode() == Result.OPERATION_COMPLETED) {
				System.out.println(
						result.getProductName() + " " + result.getItemQuantity()
								+ " $" + result.getProductPrice() + " $"
								+ result.getItemTotal());
			} else {
				System.out.println("Product could not be purchased");
			}
			System.out.println("Subtotal: $" + result.getTransactionTotal());
			if (result.getOrderId() != "none") {
				System.out.println("Reordered " + result.getOrderQuantity()
						+ " of " + result.getProductName() + " as order number "
						+ result.getOrderId());
			}

		} while (yesOrNo("Check out more products?"));
		System.out
				.println("Transaction Total: " + result.getTransactionTotal());
		Request.instance().setTransactionChange(getToken("Enter cash value: "));
		result = store.getChange(Request.instance());
		System.out.println("Change owed: " + result.getTransactionChange());
	}

	/**
	 * Method to be called for processing shipments. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for processing
	 * shipments.
	 * 
	 */
	public void processShipments() {
		do {
			Request.instance().setOrderId(getToken("Enter order number"));
			Result result = store.processShipments(Request.instance());
			if (result.getResultCode() == Result.NO_ORDER_FOUND) {
				System.out.println(
						"Order not found. Shipment could not be processed");
			} else if (result.getResultCode() == Result.OPERATION_COMPLETED) {
				System.out.println("Shipment processed");
				System.out.println(
						result.getProductId() + " " + result.getProductName()
								+ " " + result.getProductStock());
			} else {
				System.out.println("Shipment could not be processed");
			}
			if (!yesOrNo("Process more shipments?")) {
				break;
			}
		} while (true);
	}

	/**
	 * Method to be called for changing a product's price. Prompts the user for
	 * the appropriate values (product id and new price) and uses the
	 * appropriate Store method for changing the product's price.
	 * 
	 */
	public void changeProductPrice() {
		Request.instance().setProductId(getToken("Enter product id"));
		Request.instance().setProductPrice(getToken("Enter new price"));
		Result result = store.changePrice(Request.instance());
		do {
			switch (result.getResultCode()) {
			case Result.NO_SUCH_PRODUCT:
				System.out.println("No such item exists in the system");
			case Result.OPERATION_FAILED:
				System.out.println("Unable to change product price");
				break;
			case Result.OPERATION_COMPLETED:
				System.out
						.println("Price changed to " + result.getProductPrice()
								+ " for the following product: "
								+ result.getProductName());//
				break;
			default:
				System.out.println("An error has occurred");
			}

			if (!yesOrNo("Change the price of another product?")) {
				break;
			}
		} while (true);

	}

	/**
	 * Method to be called for displaying all products that contained entered
	 * String. Prompts the user for a String, which then is compared to all
	 * existing products. If a product is a superString of the user string, then
	 * its contents are printed. This is done for all products in the system.
	 */

	public void getProductInformation() {
		do {
			Iterator<Result> iterator = store.getProducts();
			String productName = getToken("Enter product name");
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (result.getProductName().contains(productName)) {
					System.out.println(result.getProductName() + "  "
							+ result.getProductId() + "  "
							+ result.getProductPrice() + "  "
							+ result.getProductStock() + "  "
							+ result.getProductReorderLevel());
				}
			}
			System.out.println("End of listing");
		} while (yesOrNo("Get another product?"));
	}

	/**
	 * Method to be called for displaying all members that contained entered
	 * String. Prompts the user for a String, which then is compared to all
	 * existing members in the MemberList. If a member name is a superString of
	 * the user String, then its contents are printed. This is done for all
	 * products in the system.
	 */

	public void getMemberInformation() {
		do {
			Iterator<Result> iterator = store.getMembers();
			String memberName = getToken("Enter member name");
			while (iterator.hasNext()) {
				Result result = iterator.next();
				if (result.getMemberName().contains(memberName)) {
					System.out.println(result.getMemberName() + "  "
							+ result.getMemberAddress() + "  "
							+ result.getMemberFeePaid() + "  "
							+ result.getMemberId());
				}
			}
			System.out.println("End of listing");
		} while (yesOrNo("Lookup another Member?"));
	}

	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Store method for displaying
	 * transactions.
	 * 
	 */
	public void getTransactions() {
		Request.instance().setMemberId(getToken("Enter member id"));
		Request.instance().setStartDate(getDate(
				"Please enter the beginning date of the period for which you want records as mm/dd/yy"));
		Request.instance().setEndDate(getDate(
				"Please enter the end date of the period for which you want records as mm/dd/yy"));
		if (Request.instance().getStartDate()
				.compareTo(Request.instance().getEndDate()) > 0) {
			System.out.println("The second date must be after the first date");
		} else {
			Iterator<Transaction> result = store
					.getTransactions(Request.instance());
			while (result.hasNext()) {
				Transaction transaction = (Transaction) result.next();
				System.out.println(transaction.getDate());
				for (TransactionItem ti : transaction.getItems()) {
					System.out.println(ti.getName() + " $" + ti.getPrice() + " "
							+ ti.getQuantity() + " $" + ti.getTotal());
				}
				System.out.println(
						"Total cost: $" + transaction.getTotal() + "\n");
			}
			System.out.println("\n End of transactions \n");
		}
	}

	/**
	 * Displays all members by iterating through MemberList
	 */
	public void getMembers() {
		Iterator<Result> iterator = store.getMembers();
		System.out.println(
				"List of members (name, address, phone, id, date joined, fee paid)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getMemberName() + " "
					+ result.getMemberAddress() + " " + result.getMemberPhone()
					+ " " + result.getMemberId() + " "
					+ result.getMemberDateJoined() + " "
					+ result.getMemberFeePaid());
		}
		System.out.println("End of listing");
	}

	/**
	 * Displays all outstanding orders by iterating through OrderList.
	 */
	public void getOrders() {
		Iterator<Result> iterator = store.getOrders();
		System.out.println("List of orders (*)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getOrderId() + " "
					+ result.getOrderProductName() + " " + result.getOrderDate()
					+ " " + result.getOrderQuantity());
		}
		System.out.println("End of listing");
	}

	/**
	 * Gets and prints all products.
	 */
	public void getProducts() {
		Iterator<Result> iterator = store.getProducts();
		System.out.println("List of products (name, id, reorder level, price)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(
					result.getProductName() + " " + result.getProductId() + " "
							+ result.getProductReorderLevel() + " "
							+ result.getProductPrice() + " "
							+ result.getProductStock());
		}
		System.out.println("End of listing");
	}

	/**
	 * Method to be called for saving the Store object. Uses the appropriate
	 * Store method for saving.
	 * 
	 */
	private void save() {
		if (store.save()) {
			System.out.println(
					" The store has been successfully saved in the file StoreData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate Store
	 * method for retrieval.
	 * 
	 */
	private void retrieve() {
		try {
			if (store == null) {
				store = Store.retrieve();
				if (store != null) {
					System.out.println(
							" The store has been successfully retrieved from the file StoreData \n");
				} else {
					System.out.println("File doesnt exist; creating new store");
					store = Store.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalities.
	 * 
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ADD_MEMBER:
				addMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case ADD_PRODUCT:
				addProducts();
				break;
			case CHECK_OUT:
				checkOutProducts();
				break;
			case PROCESS_SHIPMENT:
				processShipments();
				break;
			case CHANGE_PRICE:
				changeProductPrice();
				break;
			case PRODUCT_INFORMATION:
				getProductInformation();
				break;
			case MEMBER_INFORMATION:
				getMemberInformation();
				break;
			case PRINT_TRANSACTIONS:
				getTransactions();
				break;
			case OUTSTANDING_ORDERS:
				getOrders();
				break;
			case GET_MEMBERS:
				getMembers();
				break;
			case GET_PRODUCTS:
				getProducts();
				break;
			case SAVE:
				save();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}
