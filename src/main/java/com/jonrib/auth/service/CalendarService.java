package com.jonrib.auth.service;

import java.util.Date;

import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;

public interface CalendarService {
	Object save(Calendar calendar);
	
	Calendar getCalendarByUsername(String username);
	
	Event getEventByDate(Date date, String username);
}
