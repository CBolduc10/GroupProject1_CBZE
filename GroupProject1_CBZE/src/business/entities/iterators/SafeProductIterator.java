package business.entities.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import business.entities.Product;
import business.facade.Result;

/**
 * This Iterator implementation is tailor-made to supply a "read-only" version
 * of Product objects. The user should supply an iterator to Product as the
 * parameter to the constructor.
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 *
 */
public class SafeProductIterator implements Iterator<Result> {
	private Iterator<Product> iterator;
	private Result result = new Result();

	/**
	 * The user of SafeIterator must supply an Iterator to Product.
	 * 
	 * @param iterator Iterator<Product>
	 */
	public SafeProductIterator(Iterator<Product> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			result.setProductFields(iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}

}