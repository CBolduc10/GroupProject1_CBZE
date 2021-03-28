package business.entities;

/**
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a single Transaction object corresponding to a specific member.
 *
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<TransactionItem> items = new LinkedList<TransactionItem>();;
	private Calendar date;
	private double total;
	private double payment;
	private boolean completed;
	private Iterator<TransactionItem> iterator;

	/**
	 * Creates the transaction collection with a generated date, and both total
	 * and payment set to 0.
	 * 
	 */
	public Transaction() {
		date = new GregorianCalendar();
		this.total = 0;
		this.payment = 0;
	}

	/**
	 * Getter for payment.
	 * 
	 * @return payment
	 */
	public double getPayment() {
		return payment;
	}

	/**
	 * Getter for transaction's total.
	 * 
	 * @return total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Setter for transaction's total.
	 * 
	 * @param total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Setter for payment of transaction.
	 * 
	 * @param payment
	 */
	public void setPayment(double payment) {
		this.payment = payment;
	}

	/**
	 * Checks whether this transaction is between the given dates
	 * 
	 * @param startDate The start date for which transactions are being sought
	 * @param endDate   The end date for which transactions are being sought
	 * @return true iff the date is between the two dates
	 */
	public boolean betweenDates(Calendar startDate, Calendar endDate) {
		return ((startDate.get(Calendar.YEAR) < date.get(Calendar.YEAR)
				&& endDate.get(Calendar.YEAR) > date.get(Calendar.YEAR))
				|| (startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
						&& (startDate.get(Calendar.MONTH) < date
								.get(Calendar.MONTH)
								|| (startDate.get(Calendar.MONTH) == date
										.get(Calendar.MONTH)
										&& startDate.get(Calendar.DATE) < date
												.get(Calendar.DATE))))
				|| (endDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
						&& (endDate.get(Calendar.MONTH) > date
								.get(Calendar.MONTH)
								|| (endDate.get(Calendar.MONTH) == date
										.get(Calendar.MONTH)
										&& endDate.get(Calendar.DATE) > date
												.get(Calendar.DATE)))));
	}

	/**
	 * Method to calculate the change owed after processing transaction.
	 * 
	 * @return change change owed to customer.
	 */
	public double processTransaction() {
		double change = Math.abs(total - payment);
		return change;
	}

	/**
	 * Returns an iterator to all transaction items in transaction.
	 * 
	 * @return iterator to the collection
	 */
	public List<TransactionItem> getItems() {
		return items;
	}

	/**
	 * Adds a TransactionItem object to the list. and updates total.
	 * 
	 * @param item the TransactionItem object to be added
	 */
	public boolean addItem(TransactionItem item) {
		items.add(item);
		this.total += item.getTotal();
		return true;
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return (this.date.get(Calendar.MONTH) + 1) + "/"
				+ this.date.get(Calendar.DATE) + "/"
				+ this.date.get(Calendar.YEAR);
	}

	/**
	 * String form of the transaction
	 * 
	 */
	@Override
	public String toString() {
		return "Transaction [items=" + items + ", date=" + date + ", total="
				+ total + ", payment=" + payment + ", iterator=" + iterator
				+ "]";
	}

}