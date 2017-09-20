package com.test.categories;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.test.dao.MoneyManagerDao;
import com.test.model.CategoryDetails;
import com.test.util.MessageProvider;

public class CategoryNavigator {

	private static final Logger logger = Logger.getLogger(CategoryNavigator.class);
	private List<CategoryDetails> categoryDetailsList = new ArrayList<CategoryDetails>();
	MoneyManagerDao moneyManagerDao;
	private String category;
	private Map<String,Integer> certificatesMaturedBy = new LinkedHashMap<String,Integer> ();
	private Integer maturingByDays;
	 
	
	public String launchPage(String category){
		logger.info("Launch Page Called for the Category :"+category);
		if("".equals(category) || category ==null){
			return "";
		   }else{
			   try {
				   logger.debug("Initial CategoryDetailList Size: "+categoryDetailsList.size());
				   categoryDetailsList = moneyManagerDao.getCategoryDetailsList(category); 
				   certificatesMaturedBy.put(MessageProvider.getValue("viewCertificates.default.key")
						   , Integer.parseInt(MessageProvider.getValue("viewCertificates.default.value")));
				   certificatesMaturedBy.put(MessageProvider.getValue("viewCertificates.onemonth.key")
						   , Integer.parseInt(MessageProvider.getValue("viewCertificates.onemonth.value")));
				   certificatesMaturedBy.put(MessageProvider.getValue("viewCertificates.twomonth.key")
						   , Integer.parseInt(MessageProvider.getValue("viewCertificates.twomonth.value")));
				   certificatesMaturedBy.put(MessageProvider.getValue("viewCertificates.sixmonths.key")
						   , Integer.parseInt(MessageProvider.getValue("viewCertificates.sixmonths.value")));
				   certificatesMaturedBy.put(MessageProvider.getValue("viewCertificates.oneyear.key")
						   , Integer.parseInt(MessageProvider.getValue("viewCertificates.oneyear.value")));
				   
				   logger.debug("Bank Page DetailList Size: "+categoryDetailsList.size());
			} catch (Exception e) {
				 logger.error("Exception while getting the category Details: "+e.getCause());
			}
		   }
		return category;
	}
	
	
	public String filterCertificates(){
		logger.debug("FilterCertificates Called: "+maturingByDays);
		try {
			if(maturingByDays == 0)
				 categoryDetailsList = moneyManagerDao.getCategoryDetailsList("Bank");
			else
				categoryDetailsList = moneyManagerDao.getPriorityCategoryList(maturingByDays);
		} catch (Exception e) {
			logger.error("Exception while getting List");
		}
		return "success";
	}	

	public List<CategoryDetails> getCategoryDetailsList() {
		return categoryDetailsList;
	}

	public void setCategoryDetailsList(List<CategoryDetails> categoryDetailsList) {
		this.categoryDetailsList = categoryDetailsList;
	}

	public MoneyManagerDao getMoneyManagerDao() {
		return moneyManagerDao;
	}

	public void setMoneyManagerDao(MoneyManagerDao moneyManagerDao) {
		this.moneyManagerDao = moneyManagerDao;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	} 
	public Map<String, Integer> getCertificatesMaturedBy() {
		return certificatesMaturedBy;
	}
	public void setCertificatesMaturedBy(Map<String, Integer> certificatesMaturedBy) {
		this.certificatesMaturedBy = certificatesMaturedBy;
	}

	public Integer getMaturingByDays() {
		return maturingByDays;
	}

	public void setMaturingByDays(Integer maturingByDays) {
		this.maturingByDays = maturingByDays;
	}

	 
}
