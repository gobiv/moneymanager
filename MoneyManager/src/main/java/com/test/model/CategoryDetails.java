package com.test.model;

import java.math.BigDecimal;
import java.util.Date;

public class CategoryDetails {
	
	private String certificateNumber;
	private BigDecimal depositAmount;
	private Date depositedDate;
	private Date maturityDate;
	private BigDecimal maturityAmount;
	private double interestRate;
	private String categoryName;
	private String nameUnder;
	private String maturityDateStr;
	private String depositedDateStr;
	private boolean update;
	
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Date getDepositedDate() {
		return depositedDate;
	}
	public void setDepositedDate(Date depositedDate) {
		this.depositedDate = depositedDate;
	}
	public BigDecimal getMaturityAmount() {
		return maturityAmount;
	}
	public void setMaturityAmount(BigDecimal maturityAmount) {
		this.maturityAmount = maturityAmount;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String bank) {
		this.categoryName = bank;
	}
	public String getNameUnder() {
		return nameUnder;
	}
	public void setNameUnder(String nameUnder) {
		this.nameUnder = nameUnder;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getMaturityDateStr() {
		return maturityDateStr;
	}
	public void setMaturityDateStr(String maturityDateStr) {
		this.maturityDateStr = maturityDateStr;
	}
	public String getDepositedDateStr() {
		return depositedDateStr;
	}
	public void setDepositedDateStr(String depositedDateStr) {
		this.depositedDateStr = depositedDateStr;
	}	
	public void reset(){
		this.certificateNumber = "";
		this.depositAmount = null;
		this.depositedDate = null;
		this.maturityDate= null;
		this.maturityAmount = null;
		this.interestRate = 0;
		this.categoryName= null;
		this.nameUnder = null;
		this.maturityDateStr= null;
		this.depositedDateStr= null;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean add) {
		this.update = add;
	}
	
}
