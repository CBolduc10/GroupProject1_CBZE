package business.entities.iterators;

/**
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import business.entities.Transaction;

/**
 * This class implements the Iterator interface to iterate only on items that
 * satisfy a certain predicate.
 *
 * @param <T> the type of the item to be traversed
 */
public class FilteredIterator implements Iterator<Transaction> {
	private Transaction item;
	private Predicate<Transaction> predicate;
	private Iterator<Transaction> iterator;

	/**
	 * Sets the iterator and predicate fields and positions to the first item
	 * that satisfies the predicate.
	 * 
	 * @param iterator  the iterator to the list
	 * @param predicate specifies the test
	 */
	public FilteredIterator(Iterator<Transaction> iterator,
			Predicate<Transaction> predicate) {
		this.predicate = predicate;
		this.iterator = iterator;
		getNextItem();
	}

	/**
	 * Overrides the hasNext() method. Checks for next element.
	 */
	@Override
	public boolean hasNext() {
		return item != null;
	}

	/**
	 * Method to check and return element by calling getNextItem().
	 */
	@Override
	public Transaction next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No such element");
		}
		Transaction returnValue = item;
		getNextItem();
		return returnValue;
	}

	/**
	 * This method searches for the next item that satisfies the predicate. If
	 * none is found, the item field is set to null.
	 */
	private void getNextItem() {
		while (iterator.hasNext()) {
			item = iterator.next();
			if (predicate.test(item)) {
				return;
			}
		}
		item = null;
	}

}
