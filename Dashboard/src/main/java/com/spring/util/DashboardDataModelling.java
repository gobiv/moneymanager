package com.spring.util;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class DashboardDataModelling {

	public static Date convertStringToDate(String dateStr)
			throws ParseException {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		date = formatter.parse(dateStr);
		return date;
	}

	/*
	 * public static Date addDays(Date date, int days) { MutableDateTime dt =
	 * new MutableDateTime(date); dt.addDays(days); return dt.toDate(); }
	 */

	public static void main(String[] args) {

		try {

			MongoClient mongo = new MongoClient("10.251.53.89", 27017);
			DB db = mongo.getDB("dashboard");
			DBCollection table = db.getCollection("dashboard_items");

			// create a document to store key and value
			//insertRecords(table);

			// findAndDisplay(table);

			// updateDocument(table);

			displayAllRecords(table);

			// queryBasedOnDate(table);

			// removeAllRecords(table);

			// updateEntireDocument(table);

			// findOneRecordAndDisplay(table,"Cert_10");

			// removeElement(table);

			// dropTable(table);

			// commonUtilsMethod();

			System.out.println("Done Operations");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} /*catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	private static void commonUtilsMethod() {
		BigDecimal bd = null;
		System.out.println(BigDecimal.ZERO.compareTo(bd));

	}

	private static void dropTable(DBCollection table) {
		table.drop();
		System.out.println("Table Dropped");
	}

	/**
	 * @param table
	 */
	/*
	 * private static void queryBasedOnDate(DBCollection table) { BasicDBObject
	 * queryDate = new BasicDBObject(); Date fromDate = new Date(); Date toDate
	 * = addDays(new Date(), 20); queryDate.put("maturity_date",
	 * BasicDBObjectBuilder.start("$gte", fromDate) .add("$lte", toDate).get());
	 * 
	 * DBCursor cursorDate = table.find(queryDate).sort( new
	 * BasicDBObject("maturity_date", -1));
	 * 
	 * while (cursorDate.hasNext()) { System.out.println(cursorDate.next()); } }
	 */

	/**
	 * @param table
	 */
	private static void displayAllRecords(DBCollection table) {
		DBCursor cursorAll = table.find();
		while (cursorAll.hasNext()) {
			System.out.println(cursorAll.next());
		}
	}

	/**
	 * @param table
	 */
	private static void removeAllRecords(DBCollection table) {
		table.remove(new BasicDBObject());
	}

	/**
	 * @param table
	 * @return
	 */
	private static void updateDocument(DBCollection table) {
		BasicDBObject query = new BasicDBObject();
		query.put("userName", "gobi");

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("userName", "admin");

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);
	}

	/**
	 * @param table
	 * @return
	 */
	private static void findAndDisplay(DBCollection table) {
		/**** Find and display *** */
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userName", "gobi");

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println("Name : " + cursor.next());
		}

	}

	/**
	 * @param table
	 * @throws ParseException
	 */
	private static void insertRecords(DBCollection table) throws ParseException {

		int date = 1;
		int month = 8;
		for (int i = 32; i <= 63; i++) {
			BasicDBObject document = new BasicDBObject();
			if (i == 5 || i == 8 || i == 15 || i == 25) {
				document.put(
						"description",
						"To Geeta, Mohammad, and the Cognizant team, <br> <br>"
								+ " Thank you all for your amazing work with these changes and"
								+ " enhancements to our GPE/Salesforce platform. These improvements"
								+ " add great value to our underwriting and service teams, and as"
								+ " I've said before.. in helping us to provide the best quality"
								+ " product and service to our agents and customers. <br> <br>"
								+ " Cognizant's partnership with Chubb for this project has been"
								+ " tremendous over the last few months, with many quality changes"
								+ " that have resulted. In addition to the Chubb IT team members"
								+ " involved, I also want to thank the Cognizant members that"
								+ " worked directly with Geeta on these changes. To reiterate what"
								+ " Geeta wrote below, you have been gracious in the time you have"
								+ " dedicated in supporting our needs. <br> <br>"
								+ " <h5>Karyn Alston, Digital Business Specialist, CPI Digital"
								+ " Business</h5>");
			} else {
				document.put("description", "Sample description " + i);
				document.put("last_updated_by", "Gobi");
				document.put("last_updated_time", new Date());
				document.put("item_header", "Header " + i);
			}
			document.put("dashboard_item_id", i);
			document.put("uploaded_date", convertStringToDate("0" + date + "-0"
					+ month + "-2015"));
			document.put("last_updated_by", "Gobi");
			document.put("last_updated_time", new Date());
			document.put("item_header", "Header " + i);
			table.insert(document);
			date++;
		}
	}

	public static void removeElement(DBCollection collection) {
		BasicDBObject document = new BasicDBObject();
		document.put("deposited_amount", new Double(0.0));
		collection.remove(document);
	}

	public static void updateEntireDocument(DBCollection table) {

		findOneRecordAndDisplay(table, "Cert_10");

		BasicDBObject newDocument = new BasicDBObject();
		// newDocument.put("certificate_number", "Cert_10");
		newDocument.put("deposited_amount", new Double(1.50));
		newDocument.put("maturity_amount", new Double(1.50));
		try {
			newDocument
					.put("deposited_date", convertStringToDate("10-02-2025"));
			newDocument.put("maturity_date", convertStringToDate("03-01-2026"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newDocument.put("interest_rate", new Double(9.5));
		newDocument.put("category_name", "CHASE");
		newDocument.put("category_type", "Bank");
		newDocument.put("name_under", "REKA");

		BasicDBObject query = new BasicDBObject();
		query.put("certificate_number", "Cert_10");
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		table.update(query, updateObj);

		System.out.println("\n");
		System.out
				.println("**************************AFTER UPDATE*******************************\n");

		findOneRecordAndDisplay(table, "Cert_10");
	}

	public static void findOneRecordAndDisplay(DBCollection collection,
			String data) {
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("certificate_number", data);
		DBCursor cursor = collection.find(whereQuery);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
}
