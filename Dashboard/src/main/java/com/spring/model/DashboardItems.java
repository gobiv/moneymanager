package com.spring.model;

import java.util.Comparator;
import java.util.Date;

public class DashboardItems implements Comparable {

	 private int dashboardItemId;
	 private String description;
	 private Date uploadedDate;
	 private String lastUpdatedBy;
	 private Date lastUpdatedTime;
	 private String uploadedDateStr;
	 private String itemHeader;
	 
	public int getDashboardItemId() {
		return dashboardItemId;
	}
	public void setDashboardItemId(int dashboardItemId) {
		this.dashboardItemId = dashboardItemId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date date) {
		this.uploadedDate = date;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getUploadedDateStr() {
		return uploadedDateStr;
	}
	public void setUploadedDateStr(String uploadedDateStr) {
		this.uploadedDateStr = uploadedDateStr;
	}
	public String getItemHeader() {
		return itemHeader;
	}
	public void setItemHeader(String itemHeader) {
		this.itemHeader = itemHeader;
	}
	
	public int compareTo(Object o) {
        DashboardItems items =(DashboardItems) o;
        
         if(this.uploadedDate == null || (items != null && items.uploadedDate == null))
               return 0;
        
         return (this.uploadedDate.after(items.uploadedDate)  ) ? -1: (this.uploadedDate.before(items.uploadedDate)  ) ? 1:0 ;
 }
;
	
}
