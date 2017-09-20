package com.test.dao;

import java.util.List;

import com.test.model.CategoryDetails;
import com.test.model.LoginModel;

public interface MoneyManagerDao {
	
	public LoginModel getUserNameObject(String userName) throws Exception;
	public List<String> getCategoryList() throws Exception;
	public List<CategoryDetails> getCategoryDetailsList(String category) throws Exception;
	public List<CategoryDetails> getPriorityCategoryList(int days) throws Exception;
	public CategoryDetails getCategoryDetails(String certificateNumber) throws Exception;
	public void updateCertificate(CategoryDetails categoryDetails) throws Exception;
	public void deleteCertificate(String certificateNumber) throws Exception;
}
