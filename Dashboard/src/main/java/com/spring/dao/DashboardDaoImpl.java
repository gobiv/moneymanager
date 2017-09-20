package com.spring.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.mongodb.DB;
import com.spring.model.DashboardItems;
import com.spring.util.DateUtils;

public class DashboardDaoImpl implements DashboardDao {

	// Injecting thru spring Container
	DB db;	

	public List<DashboardItems> getDashboardItems(Sheet sheet)
			throws Exception {
		
		List<DashboardItems> dashboardItemsList = new ArrayList<DashboardItems>();
		DashboardItems dashboardItems = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		int count = 0;
		for ( Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext(); )
 	    {	
			Row row = rowsIT.next();
	        dashboardItems = new DashboardItems();
	        
 	        for ( Iterator<Cell> cellsIT = row.cellIterator(); cellsIT.hasNext(); )
 	 	    {
 	 	            Cell cell = cellsIT.next();
 					dashboardItems.setDashboardItemId(count);
 					dashboardItems.setLastUpdatedBy("admin");
 	 	            switch (cell.getCellType()) {
 	 	            case Cell.CELL_TYPE_STRING:
 	 	            	if(cell.getColumnIndex() == 0){
 	 	            		dashboardItems.setItemHeader(cell.getStringCellValue());}
 	 	            	else if(cell.getColumnIndex() == 1){
 	 	            		dashboardItems.setDescription(cell.getStringCellValue());}
					break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							dashboardItems.setUploadedDate(cell.getDateCellValue());
							dashboardItems.setUploadedDateStr(df.format(dashboardItems.getUploadedDate()));
							dashboardItems.setUploadedDateStr(DateUtils.formatDate("dd MMM,YYYY", dashboardItems.getUploadedDate()));
						}
						break;					
					default:
					}
 	 	            dashboardItems.setLastUpdatedTime(new Date());
 	 	          
 	 	      }
 	       System.out.println(dashboardItems.getItemHeader());
 	        	dashboardItemsList.add(dashboardItems);
 	        	 count ++; 
 	      	}
				return dashboardItemsList;
 	    }
		/*DBCollection collection = db.getCollection("dashboard_items");
		DashboardItems dashboardItems = null;
		BasicDBObject searchQuery = new BasicDBObject();
		//searchQuery.put("dashboard_item_id", BasicDBObjectBuilder.start("$gte", start).add("$lte", end).get());
		DBCursor cursor = collection.find(searchQuery).sort(new BasicDBObject("uploaded_date", -1));
		
		if (cursor != null && cursor.count() > 0) {
			dashboardItemsList = new ArrayList<DashboardItems>();
			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				dashboardItems = new DashboardItems();
				dashboardItems.setDashboardItemId((Integer) dbObject.get("dashboard_item_id"));
				dashboardItems.setUploadedDate((Date) dbObject.get("uploaded_date"));
				dashboardItems.setDescription((String) dbObject.get("description"));
				dashboardItems.setLastUpdatedBy((String) dbObject.get("last_updated_by"));
				dashboardItems.setLastUpdatedTime((Date) dbObject.get("last_updated_time"));
				dashboardItems.setItemHeader((String) dbObject.get("item_header"));
				if(dashboardItems.getUploadedDate() != null){
					dashboardItems.setUploadedDateStr(DateUtil.formatDate("dd MMM,YYYY", dashboardItems.getUploadedDate()));
				}
				
				
				dashboardItemsList.add(dashboardItems);
			}
		}*/
		

	public void setDb(DB db) {
		this.db = db;
	}

}
