package com.jonrib.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.model.User;
import com.jonrib.auth.repository.RoleRepository;
import com.jonrib.auth.repository.UserRepository;
import com.jonrib.auth.service.UserDetailsServiceImpl;
import com.jonrib.auth.service.UserService;
import com.jonrib.auth.service.UserServiceImpl;
import com.jonrib.auth.validator.UserValidator;

@SpringBootTest(classes = WebApplication.class)
public class UserValidatorTests {
	private final UserRepository userRepository = Mockito.mock(UserRepository.class);
	private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
	private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
	
	private UserService userDetSer;
	private UserValidator userValidator;
	
	@BeforeEach
	void initUseCase() {
		userDetSer = new UserServiceImpl(userRepository, roleRepository, bCryptPasswordEncoder);
		userValidator = new UserValidator(userDetSer);
	}
	
	@Test
	void supportsTest() {
		assertEquals(userValidator.supports(User.class), true);
	}
	
	@Test
	void validateTest() {
		User user = new User();
		user.setUsername("");
		user.setPassword("");
		user.setPasswordConfirm("test2");
		Errors err = new Errors() {
			@Override
			public void setNestedPath(String nestedPath) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rejectValue(String field, String errorCode, String defaultMessage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rejectValue(String field, String errorCode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void reject(String errorCode, String defaultMessage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void reject(String errorCode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void pushNestedPath(String subPath) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popNestedPath() throws IllegalStateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean hasGlobalErrors() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasFieldErrors(String field) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasFieldErrors() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasErrors() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getObjectName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getNestedPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<ObjectError> getGlobalErrors() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getGlobalErrorCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public ObjectError getGlobalError() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getFieldValue(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Class<?> getFieldType(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<FieldError> getFieldErrors(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<FieldError> getFieldErrors() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getFieldErrorCount(String field) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getFieldErrorCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public FieldError getFieldError(String field) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FieldError getFieldError() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getErrorCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<ObjectError> getAllErrors() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addAllErrors(Errors errors) {
				// TODO Auto-generated method stub
				
			}
		};
		Mockito.when(userDetSer.findByUsername("")).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				return user;
			}	
		});
		userValidator.validate(user, err);
		user.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		user.setUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		userValidator.validate(user, err);
	}
	
}
