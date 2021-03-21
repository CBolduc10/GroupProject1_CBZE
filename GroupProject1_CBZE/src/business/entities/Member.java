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
 * Member represents a member of the library.
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 *
 */
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private Calendar dateJoined;
	private double feePaid;
	private static final String MEMBER_STRING = "M";
	private List<Transaction> transactions = new LinkedList<Transaction>();
	private static int idCounter;

	/**
	 * Creates a single member
	 * 
	 * @param name    name of the member
	 * @param address address of the member
	 * @param phone   phone number of the member
	 */
	public Member(String name, String address, String phone, double feePaid) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.setDateJoined(new GregorianCalendar());
		this.feePaid = feePaid;
		id = MEMBER_STRING + ++idCounter;
	}

	public boolean addTransaction(Transaction transaction) {
		return transactions.add(transaction);
	}

	/**
	 * Gets an iterator to a collection of selected transactions
	 * 
	 * @param date the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Transaction> getTransactionsOnDate(Calendar date) {
		return new FilteredIterator(transactions.iterator(),
				transaction -> transaction.onDate(date));
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

	public Calendar getDateJoined() {
		return dateJoined;
	}

	public double getFeePaid() {
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
	 * @param newName member's new address
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	/**
	 * Setter for phone
	 * 
	 * @param newName member's new phone
	 */
	public void setPhone(String newPhone) {
		phone = newPhone;
	}

	public void setDateJoined(Calendar dateJoined) {
		this.dateJoined = dateJoined;
	}

	public void setFeePaid(double feePaid) {
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

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input)
			throws IOException, ClassNotFoundException {
		idCounter = (int) input.readObject();
	}

}