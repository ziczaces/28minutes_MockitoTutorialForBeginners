package com.in28minutes.mockito;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListTest {

    @Test
    public void letsMockListSize() {
        List list = mock(List.class);
        when(list.size()).thenReturn(10);
        assertEquals(10, list.size());
    }

    @Test
    public void letsMockListSizeWithMultipleReturnValues() {
        List list = mock(List.class);
        when(list.size()).thenReturn(10).thenReturn(20);
        assertEquals(10, list.size()); // First Call
        assertEquals(20, list.size()); // Second Call
    }

    @Test
    public void letsMockListGet() {
        List<String> list = mock(List.class);
        when(list.get(0)).thenReturn("in28Minutes");
        assertEquals("in28Minutes", list.get(0));
        assertNull(list.get(1));
    }

    @Test(expected = RuntimeException.class)
    public void letsMockListGetToThrowException() {
        List<String> list = mock(List.class);
        when(list.get(Mockito.anyInt())).thenThrow(
                new RuntimeException("Something went wrong"));
        list.get(0);
    }

    @Test
    public void letsMockListGetWithAny() {
        List<String> list = mock(List.class);
        Mockito.when(list.get(Mockito.anyInt())).thenReturn("in28Minutes");
        // If you are using argument matchers, all arguments
        // have to be provided by matchers.
        assertEquals("in28Minutes", list.get(0));
        assertEquals("in28Minutes", list.get(1));
    }

    @Test
    public void bddAliases_UsingGivenWillReturn() {
        List<String> list = mock(List.class);

        //given
        given(list.get(Mockito.anyInt())).willReturn("in28Minutes");

        //then
        assertThat("in28Minutes", is(list.get(0)));
        assertThat("in28Minutes", is(list.get(0)));
    }

    @Test
    public void mc_letsMockListSize() {
        List listMock = mock(List.class);

        when(listMock.size()).thenReturn(2);
        assertEquals(2, listMock.size());
    }

    @Test
    public void mc_letsMockListSizeWithMultipleReturnValues() {
        List listMock = mock(List.class);
        when(listMock.size()).thenReturn(2).thenReturn(10);
        assertEquals(2, listMock.size());
        assertEquals(10, listMock.size());
    }

    @Test
    public void mc_letsMockListGet() {
        List listMock = mock(List.class);
        when(listMock.get(0)).thenReturn("Object position 0");
        assertEquals("Object position 0", listMock.get(0));

        //if i dont mock get(1), mockito will initialized to null.
        assertEquals(null, listMock.get(1));
    }

    @Test
    public void mc_letsMockListGetArgumentMatcher() {
        List listMock = mock(List.class);
        when(listMock.get(anyInt())).thenReturn("Object position 0");
        assertEquals("Object position 0", listMock.get(0));
        assertEquals("Object position 0", listMock.get(1));
    }

    @Test(expected = RuntimeException.class)
    public void mc_letsMockListGetToThrowException() {
        List lst = mock(List.class);
        when(lst.get(anyInt())).thenThrow(new RuntimeException("something"));

        //to make throw the error.
        lst.get(0);
    }
}
