package com.jonrib.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.model.User;
import com.jonrib.auth.repository.RoleRepository;
import com.jonrib.auth.repository.UserRepository;
import com.jonrib.auth.service.UserDetailsServiceImpl;
import com.jonrib.auth.service.UserService;
import com.jonrib.auth.service.UserServiceImpl;

@SpringBootTest(classes = WebApplication.class)
public class UserServiceTests {
	private final UserRepository userRepository = Mockito.mock(UserRepository.class);
	private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
	private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
	
	private UserService userDetSer;
	
	@BeforeEach
	void initUseCase() {
		userDetSer = new UserServiceImpl(userRepository, roleRepository, bCryptPasswordEncoder);
	}
	
	@Test
	void saveTest() {
		User usr = new User();
		usr.setUsername("test");
		Mockito.when(userRepository.save(usr)).then(new Answer<User>(){
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return usr;
			}
		});
		User savedUsr = (User) userDetSer.save(usr);
		assertEquals(savedUsr, usr);
	}
	
	@Test
	void others() {
		userDetSer.findAll();
		userDetSer.findByUsername("test");
	}
	

}
 