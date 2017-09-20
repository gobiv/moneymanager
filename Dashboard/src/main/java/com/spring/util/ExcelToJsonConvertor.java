package com.spring.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExcelToJsonConvertor{
	public JSONObject excelIntoJSON(Sheet sheet){
		JSONObject json = new JSONObject();
 	    // Iterate through the rows.
 	    JSONArray rows = new JSONArray();
 	    int count = 0;
	    String[] header = new String[10];
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    Date cellDate = new Date();
	    String formatDate = "";
	    for ( Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext(); )
 	    {
 	        Row row = rowsIT.next();
 	        // Retrieve the Header value
 	        if(count == 0){
 	        	 for ( Iterator<Cell> cellsIT = row.cellIterator(); cellsIT.hasNext();)
 	 	         {
 	        		Cell cell = cellsIT.next();
 	        		int index = cell.getColumnIndex();
 	        		header[index] = cell.getStringCellValue();
 	 	         }
 	        }
 	        if(count > 0){
 	        JSONObject jRow = new JSONObject();
 	        // Iterate through the cells.
 	        JSONArray cells = new JSONArray();
 	        for ( Iterator<Cell> cellsIT = row.cellIterator(); cellsIT.hasNext(); )
 	        {
 	            Cell cell = cellsIT.next();
 	            switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:							
							cells.put(cell.getStringCellValue());
						break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								cellDate = cell.getDateCellValue();
								formatDate = df.format(cellDate);
								cells.put(formatDate);
							}else {
								cells.put(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							cells.put(cell.getBooleanCellValue());
							break;
						default:
						}        	
 	            switch(cell.getColumnIndex()){
 	            	case 0:
 	            		jRow.put( header[0], cells.get(0)); 
 	            		break;
 	            	case 1:
 	            		jRow.put(header[1], cells.get(1) );
 	            		break;
 	            	case 2:
 	            		jRow.put(header[2], cells.get(2));
 	            		break;
 	            	case 3:
 	            		jRow.put(header[3], cells.get(3));
 	            		break;
 	            	default:
 	            }  	           
 	        }
 	        rows.put(jRow);
 	        }
 	        count ++;
 	    }
 	    // Create the JSON.
 	    json.put(sheet.getSheetName(),rows);
 	    
 	    return json;
	}

}