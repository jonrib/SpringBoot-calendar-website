package com.jonrib.auth.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;
import com.jonrib.auth.model.User;
import com.jonrib.auth.service.CalendarService;
import com.jonrib.auth.service.SecurityService;
import com.jonrib.auth.service.UserService;


@Controller
public class DataController {
	 @Autowired
	 private SecurityService securityService;
	 
	 @Autowired
	 private CalendarService calendarService;
	 
	 @Autowired
	 private UserService userService;
	 
	 @RequestMapping(value = "/getCalendar")
	 public ResponseEntity<Calendar> getCalendar() {
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
	     return new ResponseEntity<Calendar>(cal, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "/getAllUsers")
	 public ResponseEntity<String> getAllUsers(@RequestParam String usernames){
		 String[] allUserNames = usernames.split(",");
		 for (String user : allUserNames) {
			 if (userService.findByUsername(user.trim()) == null)
				 return new ResponseEntity<String>("not found", HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 return new ResponseEntity<String>("success", HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "/addEvent")
	 public ResponseEntity<String> addEvent(@RequestParam String title, @RequestParam String start, @RequestParam String users, @RequestParam String eventDescription, @RequestParam String type) throws UnsupportedEncodingException, ParseException {
		 Event event = new Event();
		 String loggedIn = securityService.findLoggedInUsername();
		 event.setTitle(URLDecoder.decode(title, "UTF-8"));
		 Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(URLDecoder.decode(start, "UTF-8"));
		 event.setStart(date);
		 event.setSingle(true);
		 event.setDescription(eventDescription);
		 event.setEventOwner(loggedIn);
		 event.setType(Integer.parseUnsignedInt(type));
		 
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
		 cal.getEvents().add(event);
		 
		 calendarService.save(cal);
		 
		 Event evnt = new Event();
		 evnt.setTitle(URLDecoder.decode(title, "UTF-8"));
		 Date dat = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(URLDecoder.decode(start, "UTF-8"));
		 evnt.setStart(dat);
		 evnt.setSingle(false);
		 evnt.setDescription(eventDescription);
		 evnt.setEventOwner(loggedIn);
		 evnt.setType(Integer.parseUnsignedInt(type));
		 
		 String[] usernames = users.split(",");
		 for (String user : usernames) {
			 if (!user.equals("") && !user.equals(loggedIn)) {
				 Calendar usrCal = calendarService.getCalendarByUsername(user.trim());
				 usrCal.getEvents().add(evnt);
				 calendarService.save(usrCal);
			 }
		 }
		 
		 
		 
		 return new ResponseEntity<String>(calendarService.getEventByDate(date, securityService.findLoggedInUsername()).getId().toString(), HttpStatus.OK);
		 
	 }
	 
	 @RequestMapping(value = "/removeEvent")
	 public void removeEvent(@RequestParam String id) throws UnsupportedEncodingException, ParseException {
		 Event aevent = null;
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
		 for (Event event : cal.getEvents()) {
			 if (event.getId() == Integer.parseInt(id)) {
				 aevent = event;
				 break;
			 }
		 }
		 
		 cal.getEvents().remove(aevent);
		 calendarService.save(cal);
		 
	 }
	 
	 @RequestMapping(value = "/approveEvent")
	 public void approveEvent(@RequestParam String id) throws UnsupportedEncodingException, ParseException {
		 Event sideEvent = null;
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
		 for (Event event : cal.getEvents()) {
			 if (event.getId() == Integer.parseInt(id)) {
				 sideEvent = event;
				 break;
			 }
		 }
		 sideEvent.setSingle(true);
		 //cal.getEvents().remove(aevent);
		 calendarService.save(cal);
		 
		 Event mainEvent = null;
		 cal = calendarService.getCalendarByUsername(sideEvent.getEventOwner());
		 for (Event event : cal.getEvents()) {
			 if (event.getStart().compareTo(sideEvent.getStart()) == 0 && event.getTitle().equals(sideEvent.getTitle())) {
				 mainEvent = event;
				 break;
			 }
		 }
		 mainEvent.setDescription(mainEvent.getDescription() + " {" + securityService.findLoggedInUsername() + " approved!} ");
		 //cal.getEvents().remove(aevent);
		 calendarService.save(cal);
		 
	 }
	 
	 @RequestMapping(value = "/disapproveEvent")
	 public void disapproveEvent(@RequestParam String id, @RequestParam String comm) throws UnsupportedEncodingException, ParseException {
		 Event sideEvent = null;
		 Calendar cal = calendarService.getCalendarByUsername(securityService.findLoggedInUsername());
		 for (Event event : cal.getEvents()) {
			 if (event.getId() == Integer.parseInt(id)) {
				 sideEvent = event;
				 break;
			 }
		 }
		 
		 cal.getEvents().remove(sideEvent);
		 calendarService.save(cal);
		 
		 Event mainEvent = null;
		 cal = calendarService.getCalendarByUsername(sideEvent.getEventOwner());
		 for (Event event : cal.getEvents()) {
			 if (event.getStart().compareTo(sideEvent.getStart()) == 0 && event.getTitle().equals(sideEvent.getTitle())) {
				 mainEvent = event;
				 break;
			 }
		 }
		 mainEvent.setDescription(mainEvent.getDescription() + " {" + securityService.findLoggedInUsername() + " denied! with comment: "+comm+"} ");
		 //cal.getEvents().remove(aevent);
		 calendarService.save(cal);
		 
	 }

}
