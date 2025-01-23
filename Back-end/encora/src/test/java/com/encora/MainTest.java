package com.encora;

import com.encora.controller.ToDoController;
import com.encora.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Collections; // Make sure this import is present

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class MainTest {

	@Autowired
	private ApplicationContext applicationContext;

	@MockBean
	private ToDoService toDoService;

	@Autowired
	private ToDoController toDoController;

	@Test
	void contextLoads() {
		// This test ensures that the application context loads successfully
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void toDoControllerLoads() {
		// This test ensures that the ToDoController bean is loaded into the context
		assertThat(toDoController).isNotNull();
	}

	@Test
	void toDoServiceMocking() {
		// This test demonstrates how to mock a service and verify interactions
		when(toDoService.getAllToDos()).thenReturn(Collections.emptyList());

		assertThat(toDoController.getAllToDos().getBody()).isEmpty();

		verify(toDoService, times(1)).getAllToDos();
	}
}