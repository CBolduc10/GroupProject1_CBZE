package business.entities.iterators;

/**
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

import business.entities.Order;
import business.facade.Result;

/**
 * This Iterator implementation is tailor-made to supply a "read-only" version
 * of Order objects. The user should supply an iterator to Order as the
 * parameter to the constructor.
 *
 */
public class SafeOrderIterator implements Iterator<Result> {
	private Iterator<Order> iterator;
	private Result result = new Result();

	/**
	 * The user of SafeIterator must supply an Iterator to Order.
	 * 
	 * @param iterator Iterator<Order>
	 */
	public SafeOrderIterator(Iterator<Order> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			result.setOrderFields(iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}

}
