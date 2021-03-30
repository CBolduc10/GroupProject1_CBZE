package business.facade;

/**
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import java.util.Calendar;

/**
 * This class is used for requesting many of the results of the store system's
 * business logic to user interface. It is a singleton
 * 
 * At present, the Request object transfers values for selected fields of
 * Member, Product, TransactionItem and Order. The applicable fields are found
 * in DataTransfer.
 *
 */
public class Request extends DataTransfer {
	private static Request request;
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

	/**
	 * Getter for start date.
	 * 
	 * @return startDate
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * Setter for start date.
	 * 
	 * @param startDate
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter for end date.
	 * 
	 * @return endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * Setter for end date.
	 * 
	 * @param endDate
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}
