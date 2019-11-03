package com.jonrib.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.model.Calendar;
import com.jonrib.auth.model.Event;
import com.jonrib.auth.model.User;
import com.jonrib.auth.repository.CalendarRepository;
import com.jonrib.auth.service.CalendarServiceImpl;
import com.jonrib.auth.service.SecurityServiceImpl;
import com.jonrib.auth.service.UserDetailsServiceImpl;

@SpringBootTest(classes = WebApplication.class)
public class SecurityServiceTests {
	SecurityServiceImpl securityServiceImpl;
	private final UserDetailsServiceImpl userDetails = Mockito.mock(UserDetailsServiceImpl.class);
	@Autowired
    private AuthenticationManager authenticationManager;


	@BeforeEach
	void initUseCase() {
		securityServiceImpl = new SecurityServiceImpl(userDetails, authenticationManager);
	}


	@Test
	void findLoggedInUsernameTestNull() {
		String loggedIn = securityServiceImpl.findLoggedInUsername();
		assertEquals(null, loggedIn);
	}

	@Test
	void findLoggedInUsernameTest() {
		Authentication auth = new Authentication() {

			@Override
			public String getName() {
				return "test";
			}

			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isAuthenticated() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getCredentials() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		SecurityContextHolder.getContext().setAuthentication(auth);
		String loggedIn = securityServiceImpl.findLoggedInUsername();
		assertEquals(loggedIn, "test");
	}
}
