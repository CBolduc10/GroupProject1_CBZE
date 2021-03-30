package business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.iterators.FilteredIterator;

/**
 * Member represents a member (customer) of the store.
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 *
 */
public class Member implements Serializable, Matchable<String> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private Calendar dateJoined;
	private String feePaid;
	private static final String MEMBER_STRING = "M";
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private static int idCounter;

	/**
	 * Creates a single member, generating id and corresponding date joined.
	 * 
	 * @param name    name of the member
	 * @param address address of the member
	 * @param phone   phone number of the member
	 * @param feePaid fee paid by member
	 */
	public Member(String name, String address, String phone, String feePaid) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.setDateJoined(new GregorianCalendar());
		this.feePaid = feePaid;
		id = MEMBER_STRING + ++idCounter;
	}

	/**
	 * Adds a transaction to the member's collection of transactions.
	 * 
	 * @param transaction
	 * @return true or false
	 */
	public boolean addTransaction(Transaction transaction) {
		return transactions.add(transaction);
	}

	/**
	 * Returns the current or last transaction for the member.
	 * 
	 * @return transaction object
	 */
	public Transaction getCurrentTransaction() {
		return transactions.get(transactions.size() - 1);
	}

	/**
	 * Removes the current/last transaction.
	 */
	public void removeCurrentTransaction() {
		transactions.remove(getCurrentTransaction());
	}

	/**
	 * Checks if the member's collection of transactions is empty
	 * 
	 * @return true or false
	 */
	public boolean isEmpty() {
		return this.transactions.isEmpty();
	}

	/**
	 * Gets an iterator to a collection of selected transactions
	 * 
	 * @param startDate the start date for which the transactions have to be
	 *                  retrieved
	 * @param endDate   date the end date for which the transactions have to be
	 *                  retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Transaction> getTransactionsBetweenDates(Calendar startDate,
			Calendar endDate) {
		return new FilteredIterator(transactions.iterator(),
				transaction -> transaction.betweenDates(startDate, endDate));
	}

	/**
	 * Returns the list of all transactions for this member.
	 * 
	 * @return the iterator to the list of Transaction objects
	 */
	public Iterator<Transaction> getTransactions() {
		return transactions.iterator();
	}

	/**
	 * Getter for name
	 * 
	 * @return member name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for phone number
	 * 
	 * @return phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Getter for address
	 * 
	 * @return member address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Getter for id
	 * 
	 * @return member id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the dateJoined as a String
	 * 
	 * @return dateJoined with month, date, and year
	 */
	public String getDateJoined() {
		return ((this.dateJoined.get(Calendar.MONTH) + 1) + "/"
				+ this.dateJoined.get(Calendar.DATE) + "/"
				+ this.dateJoined.get(Calendar.YEAR));
	}

	/**
	 * Getter for fee paid
	 * 
	 * @return fee paid
	 */
	public String getFeePaid() {
		return feePaid;
	}

	/**
	 * Setter for name
	 * 
	 * @param newName member's new name
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Setter for address
	 * 
	 * @param newAddress member's new address
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	/**
	 * Setter for phone
	 * 
	 * @param newPhone member's new phone
	 */
	public void setPhone(String newPhone) {
		phone = newPhone;
	}

	/**
	 * Setter for date Joined
	 * 
	 * @param dateJoined Calendar object for date joined
	 */
	public void setDateJoined(Calendar dateJoined) {
		this.dateJoined = dateJoined;
	}

	/**
	 * Setter for fee paid by member
	 * 
	 * @param feePaid fee paid
	 */
	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}

	/**
	 * String form of the member
	 * 
	 */
	@Override
	public String toString() {
		String string = "[Member Name: " + name + " || Address: " + address
				+ " || ID: " + id + " || Phone: " + phone + " || Date Joined: "
				+ dateJoined + " || Fee Paid: " + feePaid;
		string += "] transactions: [";
		for (Iterator<Transaction> iterator = transactions.iterator(); iterator
				.hasNext();) {
			string += (Transaction) iterator.next();
		}
		string += "]";
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Checks whether the member is equal to the one supplied
	 * 
	 * @param object the member who should be compared
	 * @return true iff the member ids match
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
		Member other = (Member) object;
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
	public boolean matches(String id) {
		return this.id.equals(id);
	}

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input)
			throws IOException, ClassNotFoundException {
		idCounter = (int) input.readObject();
	}

}