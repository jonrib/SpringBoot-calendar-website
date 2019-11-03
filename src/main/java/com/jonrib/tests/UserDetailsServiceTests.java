package com.jonrib.tests;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Role;
import com.jonrib.auth.model.User;
import com.jonrib.auth.repository.CalendarRepository;
import com.jonrib.auth.repository.UserRepository;
import com.jonrib.auth.service.CalendarService;
import com.jonrib.auth.service.CalendarServiceImpl;
import com.jonrib.auth.service.UserDetailsServiceImpl;

@SpringBootTest(classes = WebApplication.class)
public class UserDetailsServiceTests {
	private final UserRepository userRepository = Mockito.mock(UserRepository.class);
	
	private UserDetailsService userSer;

	@BeforeEach
	void initUseCase() {
		userSer = new UserDetailsServiceImpl(userRepository);
	}
	
	@Test
	void loadByUserNameTest() {
		User usr = new User();
		usr.setUsername("test");
		usr.setPassword("test");
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setName("testRole");
		roles.add(role);
		usr.setRoles(roles);
		Mockito.when(userRepository.findByUsername("test")).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return usr;
			}	
		});
		userSer.loadUserByUsername("test");
	}
	
	@Test
	void loadByUserNameNullTest() {
		try {
			userSer.loadUserByUsername("test");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
