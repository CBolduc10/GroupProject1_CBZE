package business.facade;

/**
 * This class is used for returning many of the results of the library system's
 * business logic to user interface.
 * 
 * At present, the Result object returns an int code,plus values of selected
 * fields of Book and Member. They are the book title, id, borrower id, due
 * date, member name, member phone, and member id.
 * 
 * @author Brahma Dathan
 *
 */
public class Result extends DataTransfer {
	public static final int BOOK_NOT_FOUND = 1;
	public static final int BOOK_NOT_ISSUED = 2;
	public static final int BOOK_HAS_HOLD = 3;
	public static final int BOOK_ISSUED = 4;
	public static final int HOLD_PLACED = 5;
	public static final int NO_HOLD_FOUND = 6;
	public static final int OPERATION_COMPLETED = 7;
	public static final int OPERATION_FAILED = 8;
	public static final int NO_SUCH_MEMBER = 9;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}