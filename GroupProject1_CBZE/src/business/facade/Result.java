package business.facade;

/**
 * This class is used for returning many of the results of the store system's
 * business logic to user interface.
 * 
 * At present, the Result object returns an int code, plus values of selected
 * fields of Member, Product, Transaction/TransactionItem and Order. They are
 * the fields found in DataTransfer.
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 *
 */
public class Result extends DataTransfer {
	public static final int NO_SUCH_PRODUCT = 1;
	public static final int ORDER_PLACED = 2;
	public static final int NO_ORDER_FOUND = 3;
	public static final int OPERATION_COMPLETED = 4;
	public static final int OPERATION_FAILED = 5;
	public static final int NO_SUCH_MEMBER = 6;
	public static final int TRANSACTION_EMPTY = 7;
	public static final int DUPLICATE_ID = 8;
	public static final int INSUFFICIENT_FUNDS = 9;
	public static final int TRANSACTION_COMPLETE = 10;

	private int resultCode;

	/**
	 * Getter for result code
	 * 
	 * @return resultCode
	 */
	public int getResultCode() {
		return resultCode;
	}

	/**
	 * Setter for result code
	 * 
	 * @param resultCode
	 */
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}