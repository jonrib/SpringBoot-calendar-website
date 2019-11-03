package com.jonrib.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;
import com.jonrib.auth.model.User;
import com.jonrib.auth.service.CalendarService;
import com.jonrib.auth.service.SecurityService;
import com.jonrib.auth.service.UserService;
import com.jonrib.auth.web.DataController;

@SpringBootTest(classes = WebApplication.class)
public class DataControllerTests {
	 private final SecurityService securityService = Mockito.mock(SecurityService.class);
	 private final CalendarService calendarService = Mockito.mock(CalendarService.class);
	 private final UserService userService = Mockito.mock(UserService.class);
	
	private DataController dataController;	
	
	@BeforeEach
	void doBefore() {
		dataController = new DataController(securityService, calendarService, userService);
	}
	
	@Test
	void getCalendarTest() {
		assertEquals(HttpStatus.OK, dataController.getCalendar().getStatusCode());
	}
	
	@Test
	void getAllUsersNullTest() {
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, dataController.getAllUsers("test").getStatusCode());
	}
	
	@Test
	void getAllUsersTest() {
		Mockito.when(userService.findByUsername("test")).then(new Answer<User>(){
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				User usr = new User();
				usr.setUsername("test");
				return usr;
			}
		});
		assertEquals(HttpStatus.OK, dataController.getAllUsers("test").getStatusCode());
	}
	
	@Test
	void removeEventTest() {
		User usr = new User();
		usr.setUsername("test");
		Mockito.when(securityService.findLoggedInUsername()).then(new Answer<String>(){
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				
				return "test";
			}
		});
		
		Mockito.when(calendarService.getCalendarByUsername(usr.getUsername())).then(new Answer<Calendar>(){
			@Override
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar cal = new Calendar();
				Event evnt = new Event();
				evnt.setId(new Long(1));
				Set<Event> evnts = new HashSet<Event>();
				evnts.add(evnt);
				cal.setEvents(evnts);
				return cal;
			}
		});
		try {
			dataController.removeEvent("1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void approveEventTest() {
		User usr = new User();
		usr.setUsername("test");
		Mockito.when(securityService.findLoggedInUsername()).then(new Answer<String>(){
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				
				return "test";
			}
		});
		
		Mockito.when(calendarService.getCalendarByUsername(usr.getUsername())).then(new Answer<Calendar>(){
			@Override
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar cal = new Calendar();
				Event evnt = new Event();
				evnt.setId(new Long(1));
				evnt.setEventOwner("test");
				Event evnt2 = new Event();
				Date date = new Date();
				evnt.setStart(date);
				evnt2.setStart(date);
				evnt.setTitle("a");
				evnt2.setTitle("a");
				evnt2.setId(new Long(2));
				Set<Event> evnts = new HashSet<Event>();
				evnts.add(evnt);
				evnts.add(evnt2);
				cal.setEvents(evnts);
				return cal;
			}
		});
		try {
			dataController.approveEvent("1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void disapproveEventTest() {
		User usr = new User();
		usr.setUsername("test");
		Mockito.when(securityService.findLoggedInUsername()).then(new Answer<String>(){
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				
				return "test";
			}
		});
		
		Mockito.when(calendarService.getCalendarByUsername(usr.getUsername())).then(new Answer<Calendar>(){
			@Override
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar cal = new Calendar();
				Event evnt = new Event();
				evnt.setId(new Long(1));
				evnt.setEventOwner("test");
				Event evnt2 = new Event();
				Date date = new Date();
				evnt.setStart(date);
				evnt2.setStart(date);
				evnt.setTitle("a");
				evnt2.setTitle("a");
				evnt2.setId(new Long(2));
				Set<Event> evnts = new HashSet<Event>();
				evnts.add(evnt);
				evnts.add(evnt2);
				cal.setEvents(evnts);
				return cal;
			}
		});
		try {
			dataController.disapproveEvent("1", "1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void addEventTest() throws ParseException {
		User usr = new User();
		usr.setUsername("test");
		Mockito.when(securityService.findLoggedInUsername()).then(new Answer<String>(){
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				
				return "test";
			}
		});
		
		Mockito.when(calendarService.getCalendarByUsername(usr.getUsername())).then(new Answer<Calendar>(){
			@Override
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar cal = new Calendar();
				Event evnt = new Event();
				evnt.setId(new Long(1));
				evnt.setEventOwner("test");
				Event evnt2 = new Event();
				Date date = new Date();
				evnt.setStart(date);
				evnt2.setStart(date);
				evnt.setTitle("a");
				evnt2.setTitle("a");
				evnt2.setId(new Long(2));
				Set<Event> evnts = new HashSet<Event>();
				evnts.add(evnt);
				evnts.add(evnt2);
				cal.setEvents(evnts);
				return cal;
			}
		});
		Mockito.when(calendarService.getCalendarByUsername("test1")).then(new Answer<Calendar>(){
			@Override
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Calendar cal = new Calendar();
				Event evnt = new Event();
				evnt.setId(new Long(1));
				evnt.setEventOwner("test");
				Event evnt2 = new Event();
				Date date = new Date();
				evnt.setStart(date);
				evnt2.setStart(date);
				evnt.setTitle("a");
				evnt2.setTitle("a");
				evnt2.setId(new Long(2));
				Set<Event> evnts = new HashSet<Event>();
				evnts.add(evnt);
				evnts.add(evnt2);
				cal.setEvents(evnts);
				return cal;
			}
		});
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm");
		Mockito.when(calendarService.getEventByDate(format.parse("1997-02-08 12:10") , "test")).then(new Answer<Event>(){
			@Override
			public Event answer(InvocationOnMock invocation) throws Throwable {
				Calendar cal = new Calendar();
				Event evnt = new Event();
				evnt.setId(new Long(1));
				evnt.setEventOwner("test");
				Event evnt2 = new Event();
				Date date = new Date();
				evnt.setStart(date);
				evnt2.setStart(date);
				evnt.setTitle("a");
				evnt2.setTitle("a");
				evnt2.setId(new Long(2));
				Set<Event> evnts = new HashSet<Event>();
				evnts.add(evnt);
				evnts.add(evnt2);
				cal.setEvents(evnts);
				return evnt;
			}
		});
		try {
			assertEquals(HttpStatus.OK, dataController.addEvent("1", "1997-02-08 12:10", "test1", "1", "1").getStatusCode());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
