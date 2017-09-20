package com.test.categories;

import org.apache.log4j.Logger;

import com.test.dao.MoneyManagerDao;
import com.test.model.CategoryDetails;
import com.test.util.CommonUtil;

public class BankAction {

	private static final Logger logger = Logger.getLogger(BankAction.class);
	MoneyManagerDao moneyManagerDao;
	private CategoryDetails categoryDetails;
	private String certificateNumber;
	
	
	public String updateCertificate(){
		logger.info("BankAction Called for Adding new Certificate");
		try {
			moneyManagerDao.updateCertificate(categoryDetails);	
		} catch (Exception e) {
			 CommonUtil.handleErrorMessage(e.getMessage(), null);
			 return "failure";
		}
		
		return "success";
	}
	public String loadCertificatePage(String certificateNumber){
		logger.info("BankAction Called for Loading Certificate Page : "+certificateNumber);
			try {
				categoryDetails = moneyManagerDao.getCategoryDetails(certificateNumber);
			} catch (Exception e) {
				logger.error("Exception while getting the record: "+e.getCause());
			}
		
		return "success";
	}
	
	public String deleteCertificate(String certificateNumber){
		logger.info("Delete Certificate Called"+certificateNumber);
			try {
				moneyManagerDao.deleteCertificate(certificateNumber);
			} catch (Exception e) {
				logger.error("Exception while getting the record: "+e.getCause());
			}
		
		return "success";
	}
	public String loadNewCertificatePage(){
		categoryDetails = new CategoryDetails();
		categoryDetails.setUpdate(false);
				return "success";
	}
	
	public MoneyManagerDao getMoneyManagerDao() {
		return moneyManagerDao;
	}

	public void setMoneyManagerDao(MoneyManagerDao moneyManagerDao) {
		this.moneyManagerDao = moneyManagerDao;
	}
	public CategoryDetails getCategoryDetails() {
		return categoryDetails;
	}
	public void setCategoryDetails(CategoryDetails categoryDetails) {
		this.categoryDetails = categoryDetails;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	
}
