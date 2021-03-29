package business.tests;

import business.entities.Member;
import business.entities.Product;
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;

public class AutomatedTester {
	private Store store;
	private String[] memberNames = { "Colin", "Ethan", "Zach", "Brian",
			"Brahma", "John" };
	private String[] memberAddresses = { "2641", "1165", "7798", "0445", "4653",
			"4889" };
	private String[] memberPhones = { "651500", "789552", "744158", "466573",
			"892736", "755889" };
	private String[] memberFeePaid = { "y", "y", "y", "y", "y", "n" };
	private String[] memberDateJoined = { "03/28/2021", "02/15/2021",
			"03/01/2021", "03/04/2021", "01/15/2021", "12/29/2020" };
	private Member[] members = new Member[6];
	private String[] productName = { "prod1", "prod2", "prod3", "prod4",
			"prod5", "prod6", "prod7", "prod8", "prod9", "prod10", "prod11",
			"prod12", "prod13", "prod14", "prod15", "prod16", "prod17",
			"prod18", "prod19", "prod20", "prod21" };
	private String[] productStock = { "1", "3", "5", "7", "9", "11", "12", "15",
			"8", "9", "25", "16", "14", "6", "21", "30", "14", "34", "20", "10",
			"1" };
	private String[] productPrice = { "2.50", "5.00", "7.50", "10.00", "12.50",
			"15.00", "17.50", "20.00", "22.50", "25.00", "27.50", "30.00",
			"32.50", "35.00", "37.50", "40.00", "42.50", "45.00", "47.50",
			"50.00", "55.00" };
	private String[] productReorderLevel = { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "5" };
	private String[] productId = { "P1", "P2", "P3", "P4", "P5", "P6", "P7",
			"P8", "P9", "P10", "P11", "P12", "P13", "P14", "P15", "P16", "P17",
			"P18", "P19", "P20", "P21" };
	private Product[] products = new Product[21];
	private String[] shipmentRecieve = { "O1", "O2", "O3", "O4", "O5", "O6",
			"O7", "O8", "O9", "O10", "O11", "O12", "O13", "O14", "O15", "O16",
			"O17", "O18" };
	private String[] checkOutListId = { "P1", "P3", "P4" };
	private String[] checkOutListQuantity = { "3", "5", "8" };

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
			// System.out.println(result.getMemberName().equals(memberNames[count]));
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
			Request.instance()
					.setProductReorderLevel(productReorderLevel[count]);
			Request.instance().setProductId(productId[count]);
			Result result = Store.instance().addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductName().equals(productName[count]);
			assert result.getProductStock().equals(productStock[count]);
			// System.out.println(result.getProductName().equals(productName[count]));
		}
	}

	/**
	 * Tests removing a member, ID M6.
	 */
	public void testRemoveMember() {
		Request.instance().setMemberId("M6");
		Result result = Store.instance().removeMember(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		System.out.println("Member " + result.getMemberId() + " removed.");
	}

	/**
	 * Test's processing shipments. Will process 18 of the shipments generated.
	 */
	public void testProcessShipment() {
		for (int count = 0; count < shipmentRecieve.length; count++) {
			Request.instance().setOrderId(shipmentRecieve[count]);
			Result result = Store.instance()
					.processShipments(Request.instance());
			// System.out.println(result.getProductId() + " " +
			// result.getProductName() + " " + result.getProductStock());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductId() == productId[count];
			assert result.getProductName() == productName[count];
		}

	}

	/**
	 * Test's checking out a member's items.
	 */
	public void testCheckOut() {
		Request.instance().setMemberId("M1");
		Store.instance().createTransaction(Request.instance());
		for (int count = 0; count < checkOutListId.length; count++) {
			Request.instance().setProductId(checkOutListId[count]);
			Request.instance().setItemQuantity(checkOutListQuantity[count]);
			Result result = Store.instance()
					.purchaseProducts(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
		}
		Result result = Store.instance().checkTransaction(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		Request.instance().setTransactionChange("150");
		result = Store.instance().getChange(Request.instance());
		assert result.getResultCode() == Result.TRANSACTION_COMPLETE;
	}

	/**
	 * Test change product price
	 */
	public void testChangePrice() {
		Request.instance().setProductId("P21");
		Request.instance().setProductPrice("52.50");
		Result result = Store.instance().changePrice(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
	}

	public void testAll() {
		testAddMember();
		testAddProduct();
		testRemoveMember();
		testProcessShipment();
		testCheckOut();
		testChangePrice();
	}
	/*
	 * public static void main(String[] args) {
	 * System.out.println("Start Test"); new AutomatedTester().testAll(); }
	 */
}