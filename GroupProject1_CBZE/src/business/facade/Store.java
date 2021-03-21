package business.facade;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.Member;
import business.entities.Product;
import business.entities.Transaction;
import business.entities.iterators.SafeIterator;

/**
 * The facade class handling all requests from users.
 * 
 * @author Brahma Dathan
 *
 */
public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private Catalog catalog = new Catalog();
	private MemberList members = new MemberList();
	private static Store store;

	/**
	 * 
	 * @author Brahma Dathan and Sarnath Ramnath
	 * @Copyright (c) 2010
	 * 
	 *            Redistribution and use with or without modification, are
	 *            permitted provided that the following conditions are met:
	 *
	 *            - the use is for academic purpose only - Redistributions of
	 *            source code must retain the above copyright notice, this list
	 *            of conditions and the following disclaimer. - Neither the name
	 *            of Brahma Dathan or Sarnath Ramnath may be used to endorse or
	 *            promote products derived from this software without specific
	 *            prior written permission.
	 *
	 *            The authors do not make any claims regarding the correctness
	 *            of the code in this module and are not responsible for any
	 *            loss or damage resulting from its use.
	 */

	/**
	 * The collection class for Product objects
	 * 
	 * @author Brahma Dathan and Sarnath Ramnath
	 *
	 */
	private class Catalog implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Product> products = new LinkedList<Product>();

		/**
		 * Checks whether a product with a given book id exists.
		 * 
		 * @param productId the id of the product
		 * @return true iff the book exists
		 * 
		 */
		public Product search(String productId) {
			for (Iterator<Product> iterator = products.iterator(); iterator
					.hasNext();) {
				Product product = (Product) iterator.next();
				if (product.getId().equals(productId)) {
					return product;
				}
			}
			return null;
		}

		/**
		 * Inserts a product into the collection
		 * 
		 * @param product the product to be inserted
		 * @return true iff the product could be inserted. Currently always true
		 */
		public boolean insertProduct(Product product) {// insertBook
			products.add(product);
			return true;
		}

		/**
		 * Returns an iterator to all books
		 * 
		 * @return iterator to the collection
		 */
		public Iterator<Product> iterator() {
			return products.iterator();
		}

		/**
		 * String form of the collection
		 * 
		 */
		public String toString() {
			return products.toString();
		}
	}

	/**
	 * The collection class for Member objects
	 * 
	 * @author Brahma Dathan and Sarnath Ramnath
	 *
	 */
	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		/**
		 * Checks whether a member with a given member id exists.
		 * 
		 * @param memberId the id of the member
		 * @return true iff member exists
		 * 
		 */
		public Member search(String memberId) {
			for (Iterator<Member> iterator = members.iterator(); iterator
					.hasNext();) {
				Member member = iterator.next();
				if (member.getId().equals(memberId)) {
					return member;
				}
			}
			return null;
		}

		/**
		 * Inserts a member into the collection
		 * 
		 * @param member the member to be inserted
		 * @return true iff the member could be inserted. Currently always true
		 */
		public boolean insertMember(Member member) {
			members.add(member);
			return true;
		}

		public Iterator<Member> iterator() {
			return members.iterator();
		}

		/**
		 * String form of the collection
		 * 
		 */
		@Override
		public String toString() {
			return members.toString();
		}

		public boolean remove(String memberId) {
			Member member = search(memberId);
			if (member == null) {
				return false;
			} else {
				return members.remove(member);
			}
		}
	}

	/**
	 * Private for the singleton pattern Creates the catalog and member
	 * collection objects
	 */
	private Store() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static Store instance() {
		if (store == null) {
			return store = new Store();
		} else {
			return store;
		}
	}

	/**
	 * Organizes the operations for adding a product
	 * 
	 * @param title  product title
	 * @param author product id
	 * @param id     product reorder level
	 * @param price  product price
	 * @return the Product object created
	 */
	public Result addProduct(Request request) {// addBook
		Result result = new Result();
		Product product = new Product(request.getProductName(),
				request.getProductId(),
				Integer.parseInt(request.getProductReorderLevel()),
				Double.parseDouble(request.getProductPrice()));
		if (catalog.insertProduct(product)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setProductFields(product);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Organizes the operations for adding a member
	 * 
	 * @param name    member name
	 * @param address member address
	 * @param phone   member phone
	 * @param feePaid member fee paid
	 * @return the Member object created
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(),
				request.getMemberAddress(), request.getMemberPhone(),
				Double.parseDouble(request.getMemberFeePaid()));
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Removes a specific Member from the MemberList
	 * 
	 * @param memberId id of the member
	 * @return a code representing the outcome
	 */
	public Result removeMember(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		if (members.remove(request.getMemberId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Result changePrice(Request request) {
		Result result = new Result();
		Product product = catalog.search(request.getProductId());
		if (product == null) {
			result.setResultCode(Result.NO_SUCH_PRODUCT);
			return result;
		}
		if (product.setPrice(Double.parseDouble(request.getProductPrice()))) {
			result.setProductName(request.getProductName());
			result.setProductPrice(request.getProductPrice());
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Searches for a given member
	 * 
	 * @param memberId id of the member
	 * @return true iff the member is in the member list collection
	 */
	public Result searchMembership(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
		}
		return result;
	}

	/**
	 * Returns an iterator to the transactions for a specific member on a
	 * certain date
	 * 
	 * @param memberId member id
	 * @param date     date of issue
	 * @return iterator to the collection
	 */
	public Iterator<Transaction> getTransactions(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null) {
			return new LinkedList<Transaction>().iterator();
		}
		return member.getTransactionsOnDate(request.getDate());
	}

	/**
	 * Retrieves a deserialized version of the store from disk
	 * 
	 * @return a Store object
	 */
	public static Store retrieve() {
		try {
			FileInputStream file = new FileInputStream("StoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			store = (Store) input.readObject();
			Member.retrieve(input);
			return store;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Store object
	 * 
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(store);
			Member.save(output);
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns an iterator to Member info. The Iterator returned is a safe one,
	 * in the sense that only copies of the Member fields are assembled into the
	 * objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Member fields are valid.
	 */
	public Iterator<Result> getMembers() {
		return new SafeIterator<Member>(members.iterator(),
				SafeIterator.MEMBER);
	}

	/**
	 * Returns an iterator to Product info. The Iterator returned is a safe one,
	 * in the sense that only copies of the Product fields are assembled into
	 * the objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Product fields are valid.
	 */
	public Iterator<Result> getProducts() {
		return new SafeIterator<Product>(catalog.iterator(),
				SafeIterator.PRODUCT);
	}

	/**
	 * String form of the library
	 * 
	 */
	@Override
	public String toString() {
		return catalog + "\n" + members;
	}
}
