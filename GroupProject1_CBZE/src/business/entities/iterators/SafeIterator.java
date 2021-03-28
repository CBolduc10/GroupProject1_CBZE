package business.entities.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import business.entities.Member;
import business.entities.Order;
import business.entities.Product;
import business.entities.iterators.SafeIterator.Type.SafeMember;
import business.entities.iterators.SafeIterator.Type.SafeOrder;
import business.entities.iterators.SafeIterator.Type.SafeProduct;
import business.facade.Result;

/**
 * This Iterator implementation is tailor-made to supply "read-only" versions of
 * Book and Member objects. It is generic. If the user chooses Book for the
 * generic parameter, they should also supply SafeIterator.BOOK as the second
 * parameter to the constructor. Similarly, if they choose Member instead, they
 * should also choose SafeIterator.MEMBER as the second parameter of
 * constructor.
 * 
 * @author Brahma Dathan
 *
 * @param <T> Either Book, Member or Order
 */
public class SafeIterator<T> implements Iterator<Result> {
	private Iterator<T> iterator;
	private Type type;
	private Result result = new Result();
	public static final Type PRODUCT = new SafeProduct();
	public static final Type MEMBER = new SafeMember();
	public static final Type ORDER = new SafeOrder();

	/**
	 * This class is designed to ensure that the appropriate object is used to
	 * copy to the Result object.
	 * 
	 * @author Brahma Dathan
	 *
	 */
	public abstract static class Type {
		/**
		 * The copy method is used to copy the object to Result. Object is Book
		 * or Member at present.
		 * 
		 * @param result the Result object
		 * @param object the Book or Member object
		 */
		public abstract void copy(Result result, Object object);

		public static class SafeProduct extends Type {
			@Override
			public void copy(Result result, Object object) {
				Product product = (Product) object;
				result.setProductFields(product);
			}
		}

		public static class SafeMember extends Type {
			@Override
			public void copy(Result result, Object object) {
				Member member = (Member) object;
				result.setMemberFields(member);
			}
		}

		public static class SafeOrder extends Type {
			@Override
			public void copy(Result result, Object object) {
				Order order = (Order) object;
				result.setOrderFields(order);
			}
		}
	}

	/**
	 * The user of SafeIterator must supply an Iterator to Book, Member or
	 * Order. If Iterator<Book> is passed as the first parameter,
	 * SafeItearator.BOOK should be passed as the second parameter. If
	 * Iterator<Member> is passed as the first parameter, SafeItearator.MEMBER
	 * should be the second parameter. If Iterator<Order> is passed as the first
	 * parameter, SafeItearator.ORDER should be the second parameter.
	 * 
	 * @param iterator Iterator<Book>, Iterator<Member> or Iterator<Order>
	 * @param type     SafeItearator.BOOK, SafeItearator.MEMBER or
	 *                 SafeIterator.ORDER
	 */
	public SafeIterator(Iterator<T> iterator, Type type) {
		this.iterator = iterator;
		this.type = type;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			type.copy(result, iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}

}