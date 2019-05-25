package com.jonrib.auth.service;

import com.jonrib.auth.model.Calendar;

public interface CalendarService {
	void save(Calendar calendar);
	
	Calendar getCalendarByUsername(String username);
}
