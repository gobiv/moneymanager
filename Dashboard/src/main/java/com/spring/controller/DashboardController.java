package com.spring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.dao.DashboardDao;
import com.spring.model.DashboardItems;

@Controller
public class DashboardController {
 // this is test class
	List<DashboardItems> data = new ArrayList<DashboardItems>();
	int numberOfPages = 0;

	@Autowired
	DashboardDao dashboardDao;

	 @Value("${location}")
	 private String fileLocation;

	
	@RequestMapping(value = "/getDetails", method = RequestMethod.GET)
	public @ResponseBody
	String getDetails(@RequestParam("start") int start,
			@RequestParam("end") int end,@RequestParam("init") boolean init) {
		String message="";
		JSONArray jArray = null;
		List<DashboardItems> dbItems=  new ArrayList<DashboardItems>();
		File convFile = new File( fileLocation+"\\Dashboard.xlsx");
		System.out.println("getDetails Called ... ");
		try {
			InputStream inputStream = new FileInputStream(convFile.getAbsolutePath());   	 
	   	 	XSSFWorkbook myWorkBook = new XSSFWorkbook(inputStream);
	   	 	Sheet mySheet = myWorkBook.getSheet(myWorkBook.getSheetName(3));
			System.out.println("File Location: "+this.fileLocation);
			System.out.println("getDetails Called with Start as...."+start+".. and End as: "+end);
			if(init){
				data = dashboardDao.getDashboardItems(mySheet);
				if(data != null && data.size() > 0)
					Collections.sort(data);
				numberOfPages = calculateNumberOfPages();
			}
				
			System.out.println("Number of PAges: "+numberOfPages);
			
			dbItems = getPartialItems(start, end);
			
			start = end;
			end = end + 10;

			if (!CollectionUtils.isEmpty(dbItems)) {
				jArray = new JSONArray();
				JSONObject jObj = null;
				for (DashboardItems dashboardItems : dbItems) {
					jObj = new JSONObject(dashboardItems);
					jArray.put(jObj);
				}
				JSONObject countJ = new JSONObject();
				countJ.put("start", start);
				countJ.put("end", end);
				countJ.put("numberOfPages", numberOfPages-1);
				
				jArray.put(countJ);
				
				return jArray.toString();
			}

			message = "No records found";
		} catch (Exception e) {
			message = "File_not_found";
			e.printStackTrace();
		}
		return message;
	}
	
	public List<DashboardItems> getPartialItems(int start, int end) {
		List<DashboardItems> returnList = null;
		if (data != null && data.size() > 0) {

			if (start > data.size())
				return null;

			returnList = new ArrayList<DashboardItems>();
			if (data.size() > end)
				returnList = data.subList(start, end);
			else
				returnList = data.subList(start, data.size());
		}
		
		for (DashboardItems dashboardItems : returnList) {
			System.out.println("Items In Display: "+dashboardItems.getItemHeader());
		}
		
		
		return returnList;
	}
	
	public int calculateNumberOfPages(){
		int numberOfPages =0;
		if(data != null && data.size() >0 ){
			int totalSize= data.size();
			numberOfPages = totalSize /10 + (totalSize % 10 > 0 ? 1 : 0);
			
		}
		return numberOfPages;
	}
	
	@RequestMapping(value = "/logoff", method = RequestMethod.GET)
	public ModelAndView logoff(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("logout");
		request.getSession().invalidate();
		return view;
	}

	public void setDashboardDao(DashboardDao dashboardDao) {
		this.dashboardDao = dashboardDao;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
}