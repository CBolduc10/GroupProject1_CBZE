package business.tests;

import business.entities.Product;

import java.util.GregorianCalendar;

import business.entities.Member;
import business.facade.Store;
import business.facade.Request;
import business.facade.Result;

public class AutomatedTester {
	private Store store;
	private String[] memberNames = { "n1", "n2", "n3" };
	private String[] memberAddresses = { "a1", "a2", "a3" };
	private String[] memberPhones = { "p1", "p2", "p3" };
	private String[] memberFeePaid = { "y", "n", "y"};
	private String[] memberDateJoined = { "03/28/2021", "02/15/2021", "03/01/2021" };
	private Member[] members = new Member[3];
	private String[] productName = { "t1", "t2", "t3", "t4", "t5", "t6" };
	private String[] productStock = { "1", "3", "5", "7", "9", "11" };
	private String[] productPrice = { "2.50", "5.00", "7.50", "10.00", "12.50", "15.00" };
	private String[] productReorderLevel = { "1", "2", "3", "4", "5", "6" };
	private String[] productId = { "P1", "P2", "P3", "P4", "P5", "P6" };
	private Product[] products = new Product[6];
	
	/**
	 * Tests Member Creation
	 */
	public void testAddMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberAddress(memberAddresses[count]);
			Request.instance().setMemberName(memberNames[count]);
			Request.instance().setMemberPhone(memberPhones[count]);
			Request.instance().setMemberFeePaid(memberFeePaid[count]);
			Request.instance().setMemberDateJoined(memberDateJoined[count]);
			Result result = Store.instance().addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(memberNames[count]);
			assert result.getMemberPhone().equals(memberPhones[count]);
			System.out.println(result.getMemberName().equals(memberNames[count]));
		}
	}
	
	/**
	 * Tests Product creation
	 */
	public void testAddProduct() {
		for (int count = 0; count < products.length; count++) {
			Request.instance().setProductName(productName[count]);
			Request.instance().setProductStock(productStock[count]);
			Request.instance().setProductPrice(productPrice[count]);
			Request.instance().setProductReorderLevel(productReorderLevel[count]);
			Request.instance().setProductId(productId[count]);
			Result result = Store.instance().addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductName().equals(productName[count]);
			assert result.getProductStock().equals(productStock[count]);
			System.out.println(result.getProductName().equals(productName[count]));
		}
	}
	
	public void testAll() {
		testAddMember();
		testAddProduct();
	}
	/*
	public static void main(String[] args) {
		System.out.println("Start Test");
		new AutomatedTester().testAll();
	}
	*/
}