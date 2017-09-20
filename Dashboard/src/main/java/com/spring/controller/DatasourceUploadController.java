package com.spring.controller;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.util.ExcelToJsonConvertor;


@Controller
@RequestMapping("/cont")
public class DatasourceUploadController {
 
	 @Value("${location}")
	 private String location;
 
  public DatasourceUploadController(){
    System.out.println("init RestController");
  
  }
 
  
 
   @RequestMapping(value = "/upload", method = RequestMethod.POST)
   public @ResponseBody  String upload(MultipartHttpServletRequest request, HttpServletResponse response) {                 
	   String message="";
     XSSFWorkbook myWorkBook = null;
     ExcelToJsonConvertor excelToJsonConvertor = new ExcelToJsonConvertor();
     try {
    	 File convFile = new File( location+"\\Dashboard.xlsx");    	 
    	 Iterator<String> itr =  request.getFileNames();
    	 if(itr.hasNext()){
    		 MultipartFile mpf = request.getFile(itr.next());     
    		 System.out.println(mpf.getOriginalFilename() +" uploaded!");
    		 message = "upload";
    		 mpf.transferTo(convFile); 
    	 }else
    		 message="local";
    	 
    	 JSONObject json = null;
    	 StringBuilder jsonArray = new StringBuilder();
    	 jsonArray.append("{\"root\":[");
    	 if(convFile.exists()){
    		 InputStream inputStream = new FileInputStream(convFile.getAbsolutePath());   	 
    		 myWorkBook = new XSSFWorkbook(inputStream);
    		 int noOfSheets = myWorkBook.getNumberOfSheets();
    		 for (int i = 0; i < noOfSheets; i++) {
				Sheet mySheet = myWorkBook.getSheet(myWorkBook
						.getSheetName(i));
				json = excelToJsonConvertor.excelIntoJSON(mySheet);	
				jsonArray.append(json.toString());
				if(i < noOfSheets - 1)
					jsonArray.append(",");
    		 }
    		 JSONObject objMessage = new JSONObject();
    		 jsonArray.append(",");
    		 objMessage.put("message", message);
    		 jsonArray.append(objMessage);
    	 		jsonArray.append("]}");
    	 		myWorkBook.close();
    	 		return jsonArray.toString();
    	 	}
    	 else{
    		 message="-1";
    	 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
         
    return message;
  }



public String getLocation() {
	return location;
}



public void setLocation(String location) {
	this.location = location;
}
   
 
}
