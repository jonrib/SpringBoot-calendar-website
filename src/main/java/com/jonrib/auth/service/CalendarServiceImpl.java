package com.jonrib.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;
import com.jonrib.auth.repository.CalendarRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.ForAllArguments;

@Service
public class CalendarServiceImpl implements CalendarService {
	
	@Autowired
	CalendarRepository calendarRepository;
	
	@Autowired
	CalendarRepository userRepository;

	@Override
	public void save(Calendar calendar) {
		calendarRepository.save(calendar);
		
	}

	@Override
	public Calendar getCalendarByUsername(String username) {
		for (Calendar cal : calendarRepository.findAll()) {
			if (cal.getUser().getUsername().equals(username)) {
				return cal;
			}
		}
		return null;
	}

	@Override
	public Event getEventByDate(Date date, String username) {
		Calendar cale = null;
		for (Calendar cal : calendarRepository.findAll()) {
			if (cal.getUser().getUsername().equals(username)) {
				cale = cal;
			}
		}
		for (Event ev : cale.getEvents()) {
			if (ev.getStart().compareTo(date) == 0) {
				return ev;
			}
		}
		return null;
	}

}
