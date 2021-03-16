package business.facade;

import java.awt.print.Book;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.Hold;
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
		 * Removes a product from the catalog
		 * 
		 * @param productId product id
		 * @return true iff product could be removed
		 */
		public boolean removeProduct(String productId) {// removeBook
			Product product = search(productId);
			if (product == null) {
				return false;
			} else {
				return products.remove(product);
			}
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
	 * Organizes the placing of a hold
	 * 
	 * @param memberId member's id
	 * @param bookId   book's id
	 * @param duration for how long the hold should be valid in days
	 * @return indication on the outcome
	 */
	public Result placeHold(Request request) {// STRUCTURE MAY BE USEABLE FOR
												// TRANSACTIONS
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (book.getBorrower() == null) {
			result.setResultCode(Result.BOOK_NOT_ISSUED);
			return result;
		}
		Calendar date = new GregorianCalendar();
		date.add(Calendar.DATE, request.getHoldDuration());
		Hold hold = new Hold(member, book, date);
		book.placeHold(hold);
		member.placeHold(hold);
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setBookFields(book);
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
	 * Returns an iterator to the Result copy for books issued to a member
	 * 
	 * @param request - stors the member id
	 * @return iterator to the Result objects storing info about issued books
	 */
	public Iterator<Result> getProducts(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null) {
			return null;
		} else {
			return new SafeIterator<Book>(member.getBooksIssued(),
					SafeIterator.BOOK);
		}
	}

	/**
	 * Renews a book
	 * 
	 * @param memberId member id
	 * @param bookId   id of the book to be renewed
	 * 
	 * @return the book renewed
	 */
	public Result renewBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		Member member = members.search(request.getMemberId());
		result.setBookFields(book);
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		if ((book.renew(member) && member.renew(book))) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.OPERATION_FAILED);
		}
		result.setBookFields(book);
		return result;
	}

	/**
	 * Processes holds for a single book
	 * 
	 * @param bookId id of the book
	 * @return the member who should be notified
	 */
	public Result processHold(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (book.getBorrower() != null) {
			result.setResultCode(result.BOOK_ISSUED);
			return result;
		}
		Hold hold = book.getNextHold();
		if (hold == null) {
			result.setResultCode(Result.NO_HOLD_FOUND);
			return result;
		}
		hold.getMember().removeHold(request.getBookId());
		hold.getBook().removeHold(hold.getMember().getId());
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setMemberFields(hold.getMember());
		return result;
	}

	/**
	 * Removes a hold for a specific book and member combincation
	 * 
	 * @param memberId id of the member
	 * @param bookId   book id
	 * @return result of the operation
	 */
	public Result removeHold(Request request) {
		Result result = new Result();
		Member member = members.search(request.getMemberId());
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setMemberFields(member);
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (member.removeHold(request.getBookId())
				&& book.removeHold(request.getMemberId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else {
			result.setResultCode(Result.NO_HOLD_FOUND);
		}
		return result;
	}

	/**
	 * Removes all out-of-date holds.
	 * 
	 */
	private Result removeInvalidHolds() {
		Result result = new Result();
		for (Iterator<Member> memberIterator = members
				.iterator(); memberIterator.hasNext();) {
			for (Iterator<Hold> iterator = memberIterator.next()
					.getHolds(); iterator.hasNext();) {
				Hold hold = iterator.next();
				if (!hold.isValid()) {
					hold.getBook().removeHold(hold.getMember().getId());
					hold.getMember().removeHold(hold.getBook().getId());
				}
			}
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		return result;
	}

	/**
	 * Removes a specific book from the catalog
	 * 
	 * @param bookId id of the book
	 * @return a code representing the outcome
	 */
	public Result removeBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		if (book.hasHold()) {
			result.setResultCode(Result.BOOK_HAS_HOLD);
			return result;
		}
		if (book.getBorrower() != null) {
			result.setResultCode(Result.BOOK_ISSUED);
			return result;
		}
		if (catalog.removeBook(request.getBookId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Returns a single book
	 * 
	 * @param bookId id of the book to be returned
	 * @return a code representing the outcome
	 */
	public Result returnBook(Request request) {
		Result result = new Result();
		Book book = catalog.search(request.getBookId());
		if (book == null) {
			result.setResultCode(Result.BOOK_NOT_FOUND);
			return result;
		}
		result.setBookFields(book);
		Member member = book.returnBook();
		if (member == null) {
			result.setResultCode(Result.BOOK_NOT_ISSUED);
			return result;
		}
		result.setMemberFields(member);
		if (!(member.returnBook(book))) {
			result.setResultCode(Result.OPERATION_FAILED);
			return result;
		}
		if (book.hasHold()) {
			result.setResultCode(Result.BOOK_HAS_HOLD);
			return result;
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setBookFields(book);
		result.setMemberFields(member);
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
	 * Retrieves a deserialized version of the library from disk
	 * 
	 * @return a Library object
	 */
	public static Library retrieve() {
		try {
			FileInputStream file = new FileInputStream("LibraryData");
			ObjectInputStream input = new ObjectInputStream(file);
			library = (Library) input.readObject();
			Member.retrieve(input);
			return library;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * Serializes the Library object
	 * 
	 * @return true iff the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("LibraryData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(library);
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
	 * Returns an iterator to Book info. The Iterator returned is a safe one, in
	 * the sense that only copies of the Book fields are assembled into the
	 * objects returned via next().
	 * 
	 * @return an Iterator to Result - only the Book fields are valid.
	 */
	public Iterator<Result> getBooks() {
		return new SafeIterator<Book>(catalog.iterator(), SafeIterator.BOOK);
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
