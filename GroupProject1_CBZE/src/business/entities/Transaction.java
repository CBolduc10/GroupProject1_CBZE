package business.entities;

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
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a single Transaction (issue, renew, etc.)
 * 
 * @author Brahma Dathan
 *
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<TransactionItem> items;
	private Calendar date;
	private double total;
	private double payment;
	private String id;
	private Iterator<TransactionItem> iterator;

	/**
	 * Creates the transaction with a given type and book title. The date is the
	 * current date.
	 * 
	 * @param type  The type of transaction
	 * @param title The title of the book
	 * 
	 */
	public Transaction(Member member) {
		items = new LinkedList<TransactionItem>();
		date = new GregorianCalendar();
		this.id = member.getId();
		this.total = 0;
		this.payment = 0;
	}

	public double getPayment() {
		return payment;
	}

	public String getId() {
		return id;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public void setId(String id) {
		this.id = id;
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

	public double getTransactionTotal() {
		while (iterator.hasNext()) {
			TransactionItem item = iterator.next();
			total += item.getTotal();
		}
		return total;
	}

	public double processTransaction() {
		double change = total - payment;
		return change;
	}

	public List<TransactionItem> getItems() {
		return items;
	}

	/**
	 * Adds a Product object to the list.
	 * 
	 * @param product the Product object to be added
	 */
	public boolean addItem(Product product, int quantity) {
		TransactionItem item = new TransactionItem(product, quantity);
		items.add(item);
		return true;
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/"
				+ date.get(Calendar.YEAR);
	}

	/**
	 * String form of the transaction
	 * 
	 */
	@Override
	public String toString() {
		return "Transaction [items=" + items + ", date=" + date + ", total="
				+ total + ", payment=" + payment + ", id=" + id + ", iterator="
				+ iterator + "]";
	}

}