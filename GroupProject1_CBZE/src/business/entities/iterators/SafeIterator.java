package business.entities.iterators;

/**
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
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
 * Member, Order, and Product objects. It is generic. If the user chooses
 * Product for the generic parameter, they should also supply
 * SafeIterator.PRODUCT as the second parameter to the constructor. Similarly,
 * if they choose Member instead, they should also choose SafeIterator.MEMBER as
 * the second parameter of constructor. If they choose Order instead, they
 * should also choose SafeIterator.Order as the second parameter of constructor.
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
	 */
	public abstract static class Type {
		/**
		 * The copy method is used to copy the object to Result. Object is
		 * Product, Order, or Member at present.
		 * 
		 * @param result the Result object
		 * @param object the Product, Member, or Order object
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
	 * The user of SafeIterator must supply an Iterator to Member, Product or
	 * Order. If Iterator<Product> is passed as the first parameter,
	 * SafeItearator.PRODUCT should be passed as the second parameter. If
	 * Iterator<Member> is passed as the first parameter, SafeItearator.MEMBER
	 * should be the second parameter. If Iterator<Order> is passed as the first
	 * parameter, SafeItearator.ORDER should be the second parameter.
	 * 
	 * @param iterator Iterator<Product>, Iterator<Member> or Iterator<Order>
	 * @param type     SafeItearator.PRODUCT, SafeItearator.MEMBER or
	 *                 SafeIterator.ORDER
	 */
	public SafeIterator(Iterator<T> iterator, Type type) {
		this.iterator = iterator;
		this.type = type;
	}

	/**
	 * Overridden hasNext() method for iterator
	 */
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	/**
	 * Overridden next() method for iterator
	 */
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