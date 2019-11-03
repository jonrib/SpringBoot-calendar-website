package com.jonrib.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import com.jonrib.auth.WebApplication;
import com.jonrib.auth.web.UserController;

@SpringBootTest(classes = WebApplication.class)
public class UserControllerTests {
	private UserController controller = new UserController();
	
	@Test
	void welcomeTest() {
		Model model = new Model() {
			
			@Override
			public Model mergeAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsAttribute(String attributeName) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Map<String, Object> asMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAttribute(String attributeName, Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAttribute(Object attributeValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAllAttributes(Map<String, ?> attributes) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Model addAllAttributes(Collection<?> attributeValues) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		assertEquals("welcome", controller.welcome(model));
		assertEquals("calendar", controller.main(model));
		assertEquals("calendar", controller.calendar(model));
		assertEquals("registration", controller.registration(model));
		assertEquals("login", controller.login(model, "test", "test"));
		
	}

}
