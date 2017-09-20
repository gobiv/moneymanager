package com.spring.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date convertStringToDate(String dateStr)
			throws ParseException {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		date = formatter.parse(dateStr);
		return date;
	}

	public static String convertDateToString(Date date) {
		String dateStr = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		dateStr = formatter.format(date);
		return dateStr;
	}

	
	public static String formatDate(String pattern,Date date) throws ParseException{
		SimpleDateFormat ft = 
			      new SimpleDateFormat (pattern);
		return  ft.format(date);		
	}

}
