package com.in28minutes.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.in28minutes.data.api.TodoService;

public class TodoBusinessImplMockitoTest {

	@Test
	public void usingMockito() {
		TodoService todoService = mock(TodoService.class);
		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");
		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
		List<String> todos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Ranga");
		assertEquals(2, todos.size());
	}

	@Test
	public void usingMockito_UsingBDD() {
		TodoService todoService = mock(TodoService.class);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
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

		TodoService todoService = mock(TodoService.class);

		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);

		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);

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

		TodoService todoService = mock(TodoService.class);

		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");
        Mockito.when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);

		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);

		todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");
		Mockito.verify(todoService).deleteTodo(argumentCaptor.capture());

		assertEquals("Learn to Dance", argumentCaptor.getValue());
	}

	@Test
	public void mc_letsTestDeleteVerifyMethodsWithBdd() {
		//given
		TodoService todoServiceMock = mock(TodoService.class);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

		List<String> todos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos(anyString())).willReturn(todos);

		//when
		//this should delete "Learn to dance" book
		todoBusinessImpl.deleteTodosNotRelatedToSpring("any");

		//then
		verify(todoServiceMock).deleteTodo( "Learn to Dance");

		verify(todoServiceMock,Mockito.never()).deleteTodo("Learn Spring MVC");

		verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");
	}

	@Test
	public void mc_letsTestDeleteVerifyMethodsByUsingThenShould() {
		//given
		TodoService todoServiceMock = mock(TodoService.class);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

		List<String> todos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");

		given(todoServiceMock.retrieveTodos(anyString())).willReturn(todos);

		//when
		//this should delete "Learn to dance" book
		todoBusinessImpl.deleteTodosNotRelatedToSpring("any");

		//You can use "verify". Or BDD syntax: "then.should"
//		verify(todoServiceMock).deleteTodo( "Learn to Dance");
		then(todoServiceMock).should().deleteTodo("Learn to Dance");

		verify(todoServiceMock,Mockito.never()).deleteTodo("Learn Spring MVC");
//		then(todoServiceMock).should(never()).deleteTodo("Learn Spring MVC");

//		verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");
		then(todoServiceMock).should(times(1)).deleteTodo("Learn to Dance");

	}

	@Test
    public void mc_ArgumentCaptureNotBDD() {
	    //Declaring the A
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        List<String> listBooks = Arrays.asList("Learn Spring MVC",
                "Learn Spring", "Learn to Dance");

        //Using either "when" or given
        when(todoServiceMock.retrieveTodos(anyString())).thenReturn(listBooks);

        //when
        todoBusinessImpl.deleteTodosNotRelatedToSpring("anyuser");

        //then
        verify(todoServiceMock).deleteTodo( "Learn to Dance");
        verify(todoServiceMock,Mockito.never()).deleteTodo("Learn Spring MVC");
        verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");

        //Using verify to use the Captor.
        verify(todoServiceMock).deleteTodo(stringArgumentCaptor.capture());
        assertEquals("Learn to Dance", stringArgumentCaptor.getValue());
    }

    @Test
    public void mc_ArgumentCaptureBDD() {
        //Declaring the A
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //Given
        TodoService todoServiceMock = mock(TodoService.class);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        List<String> listBooks = Arrays.asList("Learn Spring MVC",
                "Learn Spring", "Learn to Dance");

        //Using either "when" or given
        given(todoServiceMock.retrieveTodos(anyString())).willReturn(listBooks);

        //when
        todoBusinessImpl.deleteTodosNotRelatedToSpring("anyuser");

        //then
        then(todoServiceMock).should().deleteTodo("Learn to Dance");
        then(todoServiceMock).should(never()).deleteTodo("Learn Spring MVC");
        then(todoServiceMock).should(times(1)).deleteTodo("Learn to Dance");

        //Using verify to use the Captor.
        then(todoServiceMock).should().deleteTodo(stringArgumentCaptor.capture());
        assertThat("Learn to Dance", is(stringArgumentCaptor.getValue()));
    }

    @Test
    public void mc_ArgumentCaptureBDDMutipleTimes() {
        //Declaring the A
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

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
