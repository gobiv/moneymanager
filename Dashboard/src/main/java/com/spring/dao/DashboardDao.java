package com.spring.dao;

import java.util.List;

import com.spring.model.DashboardItems;
import org.apache.poi.ss.usermodel.Sheet;

public interface DashboardDao {
	public List<DashboardItems> getDashboardItems(Sheet sheet) throws Exception;
}
