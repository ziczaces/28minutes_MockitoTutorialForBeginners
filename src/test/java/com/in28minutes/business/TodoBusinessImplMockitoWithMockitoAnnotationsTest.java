package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplMockitoWithMockitoAnnotationsTest {

	@Mock
	TodoService todoService;

	@InjectMocks
	TodoBusinessImpl todoBusinessImpl;

	@Captor
	ArgumentCaptor<String> stringArgumentCaptor;

	@Test
	public void usingMockito() {
		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);

		List<String> todos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Ranga");

		assertEquals(2, todos.size());
	}

	@Test
	public void usingMockito_UsingBDD() {
		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		//given
		given(todoService.retrieveTodos("Ranga")).willReturn(allTodos);

		//when
		List<String> todos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Ranga");

		//then
		assertThat(todos.size(), is(2));
	}

	@Test
	public void letsTestDeleteNow() {

		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);

		todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");

		verify(todoService).deleteTodo("Learn to Dance");

		verify(todoService, Mockito.never()).deleteTodo("Learn Spring MVC");

		verify(todoService, Mockito.never()).deleteTodo("Learn Spring");

		verify(todoService, Mockito.times(1)).deleteTodo("Learn to Dance");
		// atLeastOnce, atLeast
	}

	@Test
	public void captureArgument() {
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
				.forClass(String.class);

		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");
        Mockito.when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);

		todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");
		Mockito.verify(todoService).deleteTodo(argumentCaptor.capture());

		assertEquals("Learn to Dance", argumentCaptor.getValue());
	}

	@Test
	public void mc_letsTestDeleteVerifyMethodsWithBdd() {
		List<String> todos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		given(todoService.retrieveTodos(anyString())).willReturn(todos);

		//when
		//this should delete "Learn to dance" book
		todoBusinessImpl.deleteTodosNotRelatedToSpring("any");

		//then
		verify(todoService).deleteTodo( "Learn to Dance");

		verify(todoService,Mockito.never()).deleteTodo("Learn Spring MVC");

		verify(todoService, Mockito.times(1)).deleteTodo("Learn to Dance");
	}

	@Test
	public void mc_letsTestDeleteVerifyMethodsByUsingThenShould() {
		//given
		List<String> todos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		given(todoService.retrieveTodos(anyString())).willReturn(todos);

		//when
		//this should delete "Learn to dance" book
		todoBusinessImpl.deleteTodosNotRelatedToSpring("any");

		//You can use "verify". Or BDD syntax: "then.should"
//		verify(todoServiceMock).deleteTodo( "Learn to Dance");
		then(todoService).should().deleteTodo("Learn to Dance");

		verify(todoService,Mockito.never()).deleteTodo("Learn Spring MVC");
//		then(todoServiceMock).should(never()).deleteTodo("Learn Spring MVC");

//		verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");
		then(todoService).should(times(1)).deleteTodo("Learn to Dance");

	}

	@Test
    public void mc_ArgumentCaptureNotBDD() {
        //Given
        List<String> listBooks = Arrays.asList("Learn Spring MVC",
                "Learn Spring", "Learn to Dance");

        //Using either "when" or given
        when(todoService.retrieveTodos(anyString())).thenReturn(listBooks);

        //when
        todoBusinessImpl.deleteTodosNotRelatedToSpring("anyuser");

        //then
        verify(todoService).deleteTodo( "Learn to Dance");
        verify(todoService,Mockito.never()).deleteTodo("Learn Spring MVC");
        verify(todoService, Mockito.times(1)).deleteTodo("Learn to Dance");

        //Using verify to use the Captor.
        verify(todoService).deleteTodo(stringArgumentCaptor.capture());
        assertEquals("Learn to Dance", stringArgumentCaptor.getValue());
    }

    @Test
    public void mc_ArgumentCaptureBDD() {
        //Given
        List<String> listBooks = Arrays.asList("Learn Spring MVC",
                "Learn Spring", "Learn to Dance");

        //Using either "when" or given
        given(todoService.retrieveTodos(anyString())).willReturn(listBooks);

        //when
        todoBusinessImpl.deleteTodosNotRelatedToSpring("anyuser");

        //then
        then(todoService).should().deleteTodo("Learn to Dance");
        then(todoService).should(never()).deleteTodo("Learn Spring MVC");
        then(todoService).should(times(1)).deleteTodo("Learn to Dance");

        //Using verify to use the Captor.
        then(todoService).should().deleteTodo(stringArgumentCaptor.capture());
        assertThat("Learn to Dance", is(stringArgumentCaptor.getValue()));
    }

    @Test
    public void mc_ArgumentCaptureBDDMutipleTimes() {
        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        List<String> listBooks = Arrays.asList("Learn Spring MVC",
                "Learn Spring", "Learn to Dance", "Learn to Dance to delete too");

        //Using either "when" or given
        given(todoServiceMock.retrieveTodos(anyString())).willReturn(listBooks);

        //when
        todoBusinessImpl.deleteTodosNotRelatedToSpring("anyuser");

        //Using verify to use the Captor.
        then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getAllValues().size(), is(2));
    }
}
