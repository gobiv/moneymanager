package com.test.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.test.model.CategoryDetails;
import com.test.model.LoginModel;
import com.test.util.CommonUtil;
import com.test.util.DateUtil;

public class MoneyManagerDaoImpl implements MoneyManagerDao {

	private static final Logger logger = Logger
			.getLogger("MoneyManagerDaoImpl.class");
	//Injecting thru spring Container
	DB db;

	public LoginModel getUserNameObject(String userName) throws Exception {

		DBCollection collection = db.getCollection("user");
		LoginModel loginModel = null;
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("userName", userName);
		DBCursor cursor = collection.find(searchQuery);
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			loginModel = new LoginModel();
			loginModel.setUsername((String) dbObject.get("userName"));
			loginModel.setPassword((String) dbObject.get("password"));
		}
		return loginModel;
	}

	public List<String> getCategoryList() throws Exception {
		List<String> categories = null;
		DBCollection collection = db.getCollection("category");
		DBCursor cursor = collection.find();
		categories = new ArrayList<String>();
		logger.debug("Size of the Category :" + cursor.count());
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			categories.add((String) dbObject.get("name"));
		}
		return categories;
	}

	public List<CategoryDetails> getCategoryDetailsList(String category)
			throws Exception {
		 
		CategoryDetails categoryDetail = null;
		BasicDBObject searchQuery = new BasicDBObject();
		List<CategoryDetails> categoryDetailsList = new ArrayList<CategoryDetails>();
		
		 
		DBCollection collection = db.getCollection("category_detail");

		searchQuery.put("category_type", category);
		DBCursor cursor = collection.find(searchQuery);
		 
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			categoryDetail = new CategoryDetails();

			categoryDetail.setCertificateNumber((String) dbObject.get("certificate_number"));
			categoryDetail.setDepositAmount(new BigDecimal((Double) dbObject.get("deposited_amount")));
			categoryDetail.setDepositedDate((Date)dbObject.get("deposited_date"));
			categoryDetail.setMaturityDate((Date)dbObject.get("maturity_date"));
			categoryDetail.setDepositedDateStr(DateUtil.formatDate("dd-MM-yyy", (Date)dbObject.get("deposited_date")));
			categoryDetail.setMaturityDateStr(DateUtil.formatDate("dd-MM-yyyy",(Date)dbObject.get("maturity_date")));
			categoryDetail.setMaturityAmount(new BigDecimal((Double) dbObject.get("maturity_amount")));
			categoryDetail.setInterestRate((Double) dbObject.get("interest_rate"));
			categoryDetail.setCategoryName((String) dbObject.get("category_name"));
			categoryDetail.setNameUnder((String) dbObject.get("name_under"));
			
			
			categoryDetailsList.add(categoryDetail);
		}
		return categoryDetailsList;
	}
 
	public List<CategoryDetails> getPriorityCategoryList(int days) throws Exception {

		CategoryDetails categoryDetail = null;
		List<CategoryDetails> categoryDetailsList = new ArrayList<CategoryDetails>();

		DBCollection collection = db.getCollection("category_detail");
		Date fromDate = new Date();
		Date toDate = DateUtil.addDays(days);
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("maturity_date", BasicDBObjectBuilder.start("$gte", fromDate).add("$lte", toDate).get());
			DBCursor cursor = collection.find(query).sort(new BasicDBObject("maturity_date", -1));

			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				categoryDetail = new CategoryDetails();

				categoryDetail.setCertificateNumber((String) dbObject.get("certificate_number"));
				categoryDetail.setDepositAmount(new BigDecimal((Double) dbObject.get("deposited_amount")));
				categoryDetail.setDepositedDate((Date)dbObject.get("deposited_date"));
				categoryDetail.setMaturityDate((Date)dbObject.get("maturity_date"));
				categoryDetail.setDepositedDateStr(DateUtil.formatDate("dd-MM-yyy", (Date)dbObject.get("deposited_date")));
				categoryDetail.setMaturityDateStr(DateUtil.formatDate("dd-MM-yyyy",(Date)dbObject.get("maturity_date")));
				categoryDetail.setMaturityAmount(new BigDecimal((Double) dbObject.get("maturity_amount")));
				categoryDetail.setInterestRate((Double) dbObject.get("interest_rate"));
				categoryDetail.setCategoryName((String) dbObject.get("category_name"));
				categoryDetail.setNameUnder((String) dbObject.get("name_under"));
				categoryDetailsList.add(categoryDetail);
			}
		}catch(Exception e){
			logger.error("Exception while gettig Detais: "+e.getStackTrace());
			throw new Exception(e.getMessage());
		}
		
		return categoryDetailsList;
	}

	 
	public CategoryDetails getCategoryDetails(String certificateNumber)
			throws Exception {
		CategoryDetails categoryDetail = null;
		BasicDBObject searchQuery = new BasicDBObject();
		 
		DBCollection collection =db.getCollection("category_detail");

		searchQuery.put("certificate_number", certificateNumber);
		DBCursor cursor = collection.find(searchQuery);
		 
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			categoryDetail = new CategoryDetails();

			categoryDetail.setCertificateNumber((String) dbObject.get("certificate_number"));
			categoryDetail.setDepositAmount(new BigDecimal((Double) dbObject.get("deposited_amount")));
			categoryDetail.setDepositedDate((Date)dbObject.get("deposited_date"));
			categoryDetail.setMaturityDate((Date)dbObject.get("maturity_date"));
			categoryDetail.setDepositedDateStr(DateUtil.formatDate("dd-MM-yyy", (Date)dbObject.get("deposited_date")));
			categoryDetail.setMaturityDateStr(DateUtil.formatDate("dd-MM-yyyy",(Date)dbObject.get("maturity_date")));
			categoryDetail.setMaturityAmount(new BigDecimal((Double) dbObject.get("maturity_amount")));
			categoryDetail.setInterestRate((Double) dbObject.get("interest_rate"));
			categoryDetail.setCategoryName((String) dbObject.get("category_name"));
			categoryDetail.setNameUnder((String) dbObject.get("name_under"));
			categoryDetail.setUpdate(true);
		}
		
		return categoryDetail;
	}

	public void updateCertificate(CategoryDetails categoryDetails)
			throws Exception {

		DBCollection collection = db.getCollection("category_detail");
		CategoryDetails dbObj = getCategoryDetails(categoryDetails.getCertificateNumber());
			
		if (!categoryDetails.isUpdate()) {
			 if(dbObj != null && dbObj.getCertificateNumber().equals(categoryDetails.getCertificateNumber()))
				 throw new Exception("Certificate Number entered already exists in System.Please enter new.");
		}
		
		BasicDBObject document = new BasicDBObject();
		document.put("certificate_number", categoryDetails.getCertificateNumber());	
		document.put("deposited_amount", CommonUtil.convertBigDecimaltoFloat(categoryDetails.getDepositAmount()));
		document.put("maturity_amount", CommonUtil.convertBigDecimaltoFloat(categoryDetails.getMaturityAmount()));
		document.put("deposited_date", categoryDetails.getDepositedDate());
		document.put("maturity_date", categoryDetails.getMaturityDate());
		document.put("interest_rate", categoryDetails.getInterestRate());
		document.put("category_name", categoryDetails.getCategoryName());
		document.put("category_type","Bank");
		document.put("name_under", categoryDetails.getNameUnder());
		
		if(!categoryDetails.isUpdate()){
			collection.insert(document);
		}else{
			BasicDBObject query = new BasicDBObject();
			query.put("certificate_number", categoryDetails.getCertificateNumber());
			
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", document);

		    collection.update(query, document);
		} 
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	@Override
	public void deleteCertificate(String certificateNumber)
			throws Exception {
		DBCollection collection = db.getCollection("category_detail");
		BasicDBObject document = new BasicDBObject();
		document.put("certificate_number", certificateNumber);
		collection.remove(document);
	}
	
	/*public static void main(String[] args){
		MoneyManagerDaoImpl dao = new MoneyManagerDaoImpl();
		List<CategoryDetails> categoryDetailsList = new ArrayList<CategoryDetails>();

		try {
			categoryDetailsList = dao.getPriorityCategoryList();
			System.out.println("Size: "+categoryDetailsList.size());
			for (CategoryDetails categoryDetails : categoryDetailsList) {
				System.out.println("Name: "+categoryDetails.getCertificateNumber());
				System.out.println("Maturity Date: "+categoryDetails.getMaturityDate());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
}
