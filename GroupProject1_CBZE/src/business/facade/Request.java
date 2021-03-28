package business.facade;

import java.util.Calendar;

/**
 * This class is used for requesting many of the results of the store system's
 * business logic to user interface. It is a singleton
 * 
 * At present, the Request object returns an int code,plus values of selected
 * fields of Member, Product, TransactionItem and Order. The applicable fields
 * are found in DataTransfer.
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 *
 */
public class Request extends DataTransfer {
	private static Request request;
	private int holdDuration;
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * This is a singleton class. Hence the private constructor.
	 */
	private Request() {

	}

	/**
	 * Returns the only instance of the class.
	 * 
	 * @return the only instance
	 */
	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

	public int getHoldDuration() {
		return holdDuration;
	}

	public void setHoldDuration(int holdDuration) {
		this.holdDuration = holdDuration;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}
