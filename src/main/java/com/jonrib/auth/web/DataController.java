package com.jonrib.auth.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;
import com.jonrib.auth.service.CalendarService;
import com.jonrib.auth.service.SecurityService;


@Controller
public class DataController {
	 @Autowired
	 private SecurityService securityService;
	 
	 @Autowired
	 private CalendarService calendarService;
	 
	 @RequestMapping(value = "/getCalendar")
	 public ResponseEntity<Calendar> getCalendar() {
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
	     return new ResponseEntity<Calendar>(cal, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "/addEvent")
	 public void addEvent(@RequestParam String title, @RequestParam String start) throws UnsupportedEncodingException, ParseException {
		 Event event = new Event();
		 event.setTitle(URLDecoder.decode(title, "UTF-8"));
		 Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(URLDecoder.decode(start, "UTF-8"));
		 event.setStart(date);
		 
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
		 cal.getEvents().add(event);
		 
		 calendarService.save(cal);
		 
	 }

}
