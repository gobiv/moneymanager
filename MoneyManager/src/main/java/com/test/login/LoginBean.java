package com.test.login;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.test.dao.MoneyManagerDao;
import com.test.model.CategoryDetails;
import com.test.model.LoginModel;
import com.test.util.SessionUtil;

public class LoginBean {

	private LoginModel loginModel;
	private String message = "";

	MoneyManagerDao moneyManagerDao;

	private static final Logger logger = Logger.getLogger(LoginBean.class);
	private List<String> categories = new ArrayList<String>();
	private List<CategoryDetails> categoryDetailsList = new ArrayList<CategoryDetails>();
	private List<CategoryDetails> priorityCategoryDetailsList = new ArrayList<CategoryDetails>();
	private String category;
	
	public String authenticateUser() {

		logger.debug("authenticUser Called with User: "
				+ loginModel.getUsername());

		try {

			LoginModel loginModelDB = moneyManagerDao
					.getUserNameObject(loginModel.getUsername());

			if (loginModelDB == null) {
				System.out.println("User Object is null");
				message = "User does not exists in System";
				return "login";
			}

			String result = authentication(loginModel.getUsername(),
					loginModel.getPassword(), loginModelDB);
			
			if (result == null){
				//populate the category List
				categories = moneyManagerDao.getCategoryList();
				priorityCategoryDetailsList = moneyManagerDao.getPriorityCategoryList(8);
				
				  HttpSession session = SessionUtil.getSession();
		            session.setAttribute("username", loginModel.getUsername());
				return "success";
			}				
			else {
				message = result;
				FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_WARN,
	                    "Invalid Login!",
	                    "Please Try Again!"));
				return "login";
			}
		} catch (Exception e) {

			message = e.getMessage();
		}
		return "login";
	}

	public String authentication(String userName, String password,
			LoginModel loginModel) {
		if (userName != null && password != null) {
			if (userName.equals(loginModel.getUsername())) {
				if (password.equals(loginModel.getPassword())) {
					return null;
				} else {
					return "Please check your password";
				}
			}
		}
		return "Please enter the fields";
	}
	
	/*public String launchBankPage(String category){
		logger.info("Launch Bank Page Method Called :"+category);
		if("".equals(category) || category ==null){
			return "";
		   }else{
			   try {
				   logger.debug("Initial CategoryDetailList Size: "+categoryDetailsList.size());
				   categoryDetailsList = moneyManagerDao.getCategoryDetailsList(category);
				   logger.debug("Bank Page DetailList Size: "+categoryDetailsList.size());
			} catch (Exception e) {
				 logger.error("Exception while getting the category Details: "+e.getCause());
			}
		   }
		return "bankPage";
	}*/
 
	 public String logout() {
	      HttpSession session = SessionUtil.getSession();
	      session.invalidate();
	      return "login";
	   }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginModel getLoginModel() {
		return loginModel;
	}

	public void setLoginModel(LoginModel loginModel) {
		this.loginModel = loginModel;
	}

	public MoneyManagerDao getMoneyManagerDao() {
		return moneyManagerDao;
	}

	public void setMoneyManagerDao(MoneyManagerDao moneyManagerDao) {
		this.moneyManagerDao = moneyManagerDao;
	}

	public List<String> getCategories() {
		return categories;
	}

	public List<CategoryDetails> getCategoryDetailsList() {
		return categoryDetailsList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<CategoryDetails> getPriorityCategoryDetailsList() {
		return priorityCategoryDetailsList;
	}

	public void setPriorityCategoryDetailsList(
			List<CategoryDetails> priorityCategoryDetailsList) {
		this.priorityCategoryDetailsList = priorityCategoryDetailsList;
	}
	

}
