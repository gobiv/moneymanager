package com.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

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

	public static String  addDays(Date date, int days) {
		MutableDateTime dt = new MutableDateTime(date);
		dt.addDays(days);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
		String dtStr = fmt.print(dt);
		return dtStr;
	}
	
	public static Date  addDays(int days) {
		MutableDateTime dt = new MutableDateTime(new Date());
		dt.addDays(days);
		/*DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
		String dtStr = fmt.print(dt);*/
		return dt.toDate();
	}
	
	public static String formatDate(String pattern,Date date) throws ParseException{
		SimpleDateFormat ft = 
			      new SimpleDateFormat (pattern);
		return  ft.format(date);		
	}

}
