package ui;

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
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;

/**
 * 
 * This class implements the user interface for the Library project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 *
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));
	private static Store store;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;// ADD_BOOKS
	private static final int ADD_PRODUCT = 3;// ISSUE_BOOKS
	private static final int CHECK_OUT = 4;// RETURN_BOOKS
	private static final int PROCESS_SHIPMENT = 5;// RENEW_BOOKS
	private static final int CHANGE_PRICE = 6;// REMOVE_BOOKS
	private static final int PRODUCT_INFORMATION = 7;// PLACE_HOLD
	private static final int MEMBER_INFORMATION = 8;// REMOVE_HOLD
	private static final int PRINT_TRANSACTIONS = 9;// PROCESS_HOLD
	private static final int OUTSTANDING_ORDERS = 10;// GET_TRANSACTIONS
	private static final int GET_MEMBERS = 11;
	private static final int GET_PRODUCTS = 12;// GET_BOOKS
	private static final int SAVE = 13;
	private static final int HELP = 14;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved
	 * data. Otherwise, it gets a singleton Library object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
			retrieve();
		} else {
			store = Store.instance();
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
	 * appropriate values and uses the appropriate Library method for adding the
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
			Request.instance().setMemberId(getToken("Enter book id"));
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
	 * Method to be called for adding a book. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for adding the
	 * book.
	 * 
	 */
	public void addProducts() {
		do {
			Request.instance().setProductName(getName("Enter name"));
			Request.instance().setProductId(getToken("Enter id"));
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
	 * Method to be called for issuing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for issuing
	 * books.
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
		do {
			Request.instance().setProductId(getToken("Enter product id"));
			Request.instance()
					.setProductStock(getToken("Enter product quantity"));
			result = store.purchaseProducts(Request.instance());
			if (result.getResultCode() == Result.OPERATION_COMPLETED) {
				System.out.println("Product " + result.getProductName()
						+ " Qty " + result.getProductStock()
						+ result.getMemberName()//
						+ " checked out by " + result.getMemberName());//
			} else {
				System.out.println("Product could not be purchased");
			}
		} while (yesOrNo("Check out more products?"));
	}

	/**
	 * Method to be called for processing books. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for processing
	 * books.
	 * 
	 */
	public void processShipments() {
		do {
			Request.instance().setOrderId(getToken("Enter order id"));
			Result result = store.processHold(Request.instance());
			if (result.getResultCode() == Result.OPERATION_COMPLETED) {
				System.out
						.println("Order " + result.getOrderId() + " processed");//
			} else {
				System.out.println("No shipments left to be processed");
			}
			if (!yesOrNo("Process another shipment?")) {
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
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for displaying
	 * transactions.
	 * 
	 */
	public void getTransactions() {
		Request.instance().setMemberId(getToken("Enter member id"));
		Request.instance().setDate(getDate(
				"Please enter the beginning date of the period for which you want records as mm/dd/yy"));
		Request.instance().setDate(getDate(
				"Please enter the end date of the period for which you want records as mm/dd/yy"));
		Iterator<Transaction> result = store
				.getTransactions(Request.instance());
		while (result.hasNext()) {
			Transaction transaction = (Transaction) result.next();
			System.out.println(transaction.getType() + "   "
					+ transaction.getTitle() + "\n");//
		}
		System.out.println("\n End of transactions \n");
	}

	/**
	 * Displays all members
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
	 * Displays all outstanding orders
	 */
	public void getOrders() {
		Iterator<Result> iterator = store.getMembers();
		System.out.println("List of orders (*)");// *FIELDS GO HERE*
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getMemberName() + " "
					+ result.getMemberAddress() + " " + result.getMemberPhone()//
					+ " " + result.getMemberId() + " "
					+ result.getMemberDateJoined() + " "
					+ result.getMemberFeePaid());//
		}
		System.out.println("End of listing");
	}

	/**
	 * Gets and prints all books.
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
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
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
	 * Method to be called for retrieving saved data. Uses the appropriate
	 * Library method for retrieval.
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
