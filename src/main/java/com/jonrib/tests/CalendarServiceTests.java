package com.jonrib.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;
import com.jonrib.auth.model.User;
import com.jonrib.auth.repository.CalendarRepository;
import com.jonrib.auth.service.CalendarService;
import com.jonrib.auth.service.CalendarServiceImpl;
import static org.junit.Assert.*;

@SpringBootTest(classes = WebApplication.class)
public class CalendarServiceTests {
	private final CalendarRepository calendarRepository = Mockito.mock(CalendarRepository.class);
	private final CalendarRepository userRepository = Mockito.mock(CalendarRepository.class);

	private CalendarService calSer;

	@BeforeEach
	void initUseCase() {
		calSer = new CalendarServiceImpl(calendarRepository, userRepository);
	}
	@Test
	void saveCalendar() {
		User user = new User();
		user.setUsername("test");
		Calendar cal = new Calendar();
		cal.setEvents(new HashSet<Event>());
		cal.setUser(user);
		Event evnt = new Event();
		evnt.setDescription("test");
		evnt.setEventOwner("owner");
		evnt.setId(new Long(1));
		cal.getEvents().add(evnt);
		calSer.save(cal);
		Mockito.when(calendarRepository.save(cal)).then(new Answer<Calendar>() {
			@Override
			public Calendar answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Calendar) args[0];
			}	
		});
		Calendar savedCal = (Calendar) calSer.save(cal);
		assertEquals(savedCal, cal);
	}

	@Test
	void getByUserName() {
		User user = new User();
		user.setUsername("test");
		Calendar cal = new Calendar();
		cal.setEvents(new HashSet<Event>());
		cal.setUser(user);
		Event evnt = new Event();
		evnt.setDescription("test");
		evnt.setEventOwner("owner");
		evnt.setId(new Long(1));
		cal.getEvents().add(evnt);
		calSer.save(cal);
		Mockito.when(calendarRepository.findAll()).then(new Answer<List<Calendar>>() {
			@Override
			public List<Calendar> answer(InvocationOnMock invocation) throws Throwable {
				List<Calendar> ls = new ArrayList<Calendar>();
				ls.add(cal);
				return ls;
			}	
		});
		Calendar savedCal = calSer.getCalendarByUsername("test");
		assertEquals(savedCal, cal);
	}
	
	@Test
	void getByUserNameNull() {
		Calendar savedCal = calSer.getCalendarByUsername("test");
		assertEquals(savedCal, null);
	}
	
	@Test
	void getEventByDateNull() {
		Event savedEvnt = calSer.getEventByDate(new Date(), "test");
		assertEquals(savedEvnt, null);
	}
	
	@Test
	void getEventByDate() {
		User user = new User();
		user.setUsername("test");
		Calendar cal = new Calendar();
		cal.setEvents(new HashSet<Event>());
		cal.setUser(user);
		Date date = new Date();
		Event evnt = new Event();
		evnt.setDescription("test");
		evnt.setEventOwner("owner");
		evnt.setId(new Long(1));
		evnt.setStart(date);
		cal.getEvents().add(evnt);
		calSer.save(cal);
		Mockito.when(calendarRepository.findAll()).then(new Answer<List<Calendar>>() {
			@Override
			public List<Calendar> answer(InvocationOnMock invocation) throws Throwable {
				List<Calendar> ls = new ArrayList<Calendar>();
				ls.add(cal);
				return ls;
			}	
		});
		Event savedEvnt = calSer.getEventByDate(date, "test");
		assertEquals(savedEvnt, evnt);
	}
	
	@Test
	void getEventByDateNullSec() {
		User user = new User();
		user.setUsername("test");
		Calendar cal = new Calendar();
		cal.setEvents(new HashSet<Event>());
		cal.setUser(user);
		Date date = new Date();
		Event evnt = new Event();
		evnt.setDescription("test");
		evnt.setEventOwner("owner");
		evnt.setId(new Long(1));
		evnt.setStart(date);
		cal.getEvents().add(evnt);
		calSer.save(cal);
		Mockito.when(calendarRepository.findAll()).then(new Answer<List<Calendar>>() {
			@Override
			public List<Calendar> answer(InvocationOnMock invocation) throws Throwable {
				List<Calendar> ls = new ArrayList<Calendar>();
				ls.add(cal);
				return ls;
			}	
		});
		Event savedEvnt = calSer.getEventByDate(new Date(System.currentTimeMillis()+100), "test");
		assertEquals(null, savedEvnt);
	}
}
