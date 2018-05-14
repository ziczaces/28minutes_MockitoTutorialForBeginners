Mockito Tutorial: Learn mocking with 25 Junit Examples

https://www.udemy.com/mockito-tutorial-with-junit-examples/learn/v4/t/lecture/5678756?start=0

The step01.md to step18.md files are used for the  **section 5**

# Section 1 Introduction
•	What is JUnit?
•	Why Unit Testing?



# Section 2 Unit testing with Junit.
## @Test Annotation
- Running JUnit
- No Failure = Success
- Basic Assert methods

Create a new java class StringHelper (copy the source from “github”) and, then, we will create the Junit test

We will create it under the same package and the name convetion for this class will be the name of the class + Test

## Step 3 - Before and after.
-assertTrue and assertFalse methods
- @Before @After annotations
- @BeforeClass @AfterClass annotations

There are some methods in Junit
- @Test
- Fail
- Assert
- assertEquals(expected, result)



## step 4 JUnit Simple Test Scenarios
- Comparing Arrays
- Testing Exceptions
- Performance Unit Tests

There are some things we could change.

### Test Methods needs to be:
- Public and void
- Method name: The name should contain the method name we are testing.

## Testing two conditions under the same test
We are testing 2 conditions on the same test. When we are unit testing complex scenarios we should have separate scenarios for each conditions

### Removing duplicate code
“StringHelper” variable is duplicated.  Declare it at class level



## Step 5 JUnit Intermediate Test Scenarios - assertTrue assertFalse
-Parameterized Tests
- Test Suites

As the method, we are testing is retuning a Boolean; we can use the **assertTrue** or **assertFalse** depending on what we want to test.

On this new method, we need to do at least 3 tests in to do in order to do a full coverage.
-	String’s size is equals to 1 (e.g. A)
-	String’s size is equals to 2 (e.g AC)
-	String’s size is greater than 2 (e.g. ABC (false),  ABCD (false), ABAB (true)



## step 6 JUnit Best Practices - before and after.
- Naming Test Methods
- Highlight Important Values in Tests
- Handle Exceptions Properly
- Readable Assert Statements

@Before: This can be used if we need to do some setup before running each test.

@After: This can be use if we need to do something after running each test, e.g. killing a connection



## step 7: @BeforeClass and @AfterClass
**@BeforeClass** and **@AfterClass** will NOT be called for each test; this is called before and after all tests are run.

The methods using @BeforeClass and @AdferClass must be static methods

```java
public class QuickBeforeAfterTest {

	@BeforeClass
	public static void beforeClass(){
		System.out.println("Before Class");
	}

	@Before
	public void setup(){
		System.out.println("Before Test");
	}

	@Test
	public void test1() {
		System.out.println("test1 executed");
	}

	@Test
	public void test2() {
		System.out.println("test2 executed");
	}

	@After
	public void teardown() {
		System.out.println("After test");
	}

	@AfterClass
	public static void afterClass(){
		System.out.println("After Class");
	}

}
```



## Step 8: Comparing Arrays in Junit Tests
We are testing that an array is sorted into ascending numerical order.
We will get an error if we use the **assertEquals** that we used previously.
This happens since these two arrays are different objects in memory; although they have the same values.

This will trigger an error:

```java
@Test
public void testArraySort_RandomArray() {
  int[] numbers = { 12, 3, 4, 1 };
  int[] expected = { 1, 3, 4, 12 };
  Arrays.sort(numbers);
  assertEquals(expected, numbers);
}
```


To test array contains the save values we need to use `assertArrayEquals`

```java
  @Test
	public void testArraySort_RandomArray() {
		int[] numbers = { 12, 3, 4, 1 };
		int[] expected = { 1, 3, 4, 12 };
		Arrays.sort(numbers);
//		assertEquals(expected, numbers);
		assertArrayEquals(expected, numbers);
	}
```



## Step 9: Testing exceptions in Junit
We are testing an Null pointer exception. This could happen if we try to sort an empty array.

``` ArrayCompareTest.java
@Test
	public void testArraySort_NullArray() {
		int[] numbers = null;
		Arrays.sort(numbers);
	}
```

A better way to do this would have been to use an special annotation that Junit provides, `NullPointerException.class`.

We can declare a test where a Null Pointer exception is expected to happen by using Expected=NullPointerExcetipon.class anotation

```
@Test(expected=NullPointerException.class)
public void testArraySort_NullArray() {
  int[] numbers = null;
  Arrays.sort(numbers);
}
```

I am now expecting to get Null pointer exception on this test. If we don’t get it, this test will fail; as it happens.



## Step  10: Testing performance in Junit.
We can set  test to be perform under certain “timeout”.

In order to do that we need to add to the @Test annotation the timeout tag.
@Test(timeout=xxxxx), where xxx are milliseconds

This is a good test; we can set a benchmark and if this test fails in the future; this is means the changes made to the method is impacting performance

```
@Test(timeout=100)
public void testSort_Performance(){
  int array[] = {12,23,4};
  for(int i=1;i<=1000000;i++)
  {
    array[0] = i;
    Arrays.sort(array);
  }
}
```



## Step  11: Parameterized Tests.

if we have methods for different test where the code is the same and where the only difference is the inputs and the outputs, then we could use `Parameterized.Class`.

See below example where all the tests are the same, but they have different inputs and expected Values

```StringHelper.java
StringHelper helper;

@Before
public void before(){
  helper = new StringHelper();
}


@Test
public void testTruncateAInFirst2Positions_AinFirst2Positions() {
  assertEquals("CD", helper.truncateAInFirst2Positions("AACD"));
}

@Test
public void testTruncateAInFirst2Positions_AinFirstPosition() {
  assertEquals("CD", helper.truncateAInFirst2Positions("ACD"));
}

// ABCD => false, ABAB => true, AB => true, A => false
@Test
public void testAreFirstAndLastTwoCharactersTheSame_BasicNegativeScenario() {
  assertFalse(
      helper.areFirstAndLastTwoCharactersTheSame("ABCD"));
}

@Test
public void testAreFirstAndLastTwoCharactersTheSame_BasicPositiveScenario() {
  assertTrue(
      helper.areFirstAndLastTwoCharactersTheSame("ABAB"));
}
```

We could save code by using parameterized test.
Steps:
1.Specify that the class is going to used parameterized test: @RunWith(Parameterized.class)

```StringHelperParameterizedTest.java
@RunWith(Parameterized.class)
public class StringHelperParameterizedTest {
```

2.Specify the arguments we want to pass
To do that we need to create a static method which will hold the actual values we want to pass to our test. And this will have the @Parameters annotation so that the code will know this method will contain the parameter we want to pass to our test.

And this will have the @Parameters annotation so that the code will know this method will contain the parameter we want to pass to our test.

It will  return an Array with the conditions we want to test: **{expected, input}**

```
@Parameters
	public static Collection<String[]> testConditions() {
		String expectedOutputs[][] = {
				{ "AACD", "CD" },
				{ "ACD", "CD" } };
		return Arrays.asList(expectedOutputs);
```


3.We need to tell the code to use this Parameters
we will create private variables that will be used by the method and that will hold the information from our tests.

And the constructor will contains initialize the inputs and expected values.

```
StringHelper helper = new StringHelper();

private String input;
private String expectedOutput;

public StringHelperParameterizedTest(String input, String expectedOutput) {
  this.input = input;
  this.expectedOutput = expectedOutput;
```

The code then it will look like:

```StringHelperParameterizedTest.java
package com.in28minutes.junit.helper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StringHelperParameterizedTest {

	// AACD => CD ACD => CD CDEF=>CDEF CDAA => CDAA

	StringHelper helper = new StringHelper();

	private String input;
	private String expectedOutput;

	public StringHelperParameterizedTest(String input, String expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	@Parameters
	public static Collection<String[]> testConditions() {
		String expectedOutputs[][] = {
				{ "AACD", "CD" },
				{ "ACD", "CD" } };
		return Arrays.asList(expectedOutputs);
	}

	@Test
	public void testTruncateAInFirst2Positions() {
		assertEquals(expectedOutput,
				helper.truncateAInFirst2Positions(input));
	}
}

```

You cannot have 2 different parameterized test on the same class. We need to create a new class if we want to do that.



# Section 3. Getting Ready for Mockito
I ignored it.



# Section 4. Need for Mockito.
## step 03 - Stubbing Example
i ignored it.


## step 04 - first mockito
This no longer uses the stub, it uses a Mock.

**When** is used to stub a method called with a return values
**thenReturn** the value that we want want to return back.

```TodoBusinessImplMockitoTest.Java
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
```

We can make the function to retrieve different values. This the flexibility that mock brings.



# Section 5. Mockito basis.
we will used the step1.md files to step18.md

## What You Will Learn during this Step:
- A few mockito examples mocking List class
- Multiple return values
- Introduction to Argument Matchers
- stub method
- Throwing exceptions

## Exercises
- What if we combine a matcher with hardcoded value when stubbing.
- Mock a few other List Methods.
- What happens if an unstubbed method is called?
- By default, for all methods that return a value, a mock will return either null, a a primitive/primitive wrapper value, or an empty collection, as appropriate. For example 0 for an int/Integer and false for a boolean/Boolean.



## step 05 - Stubbing variations wit Mockito. Argument Matcher.

### Mocking List API.  
We are mocking a **List** of interfaces from Java library

This will mock the first call of `list.size`

ListTest.java
```java
@Test
	public void letsMockListSize() {
		List list = mock(List.class);
		when(list.size()).thenReturn(10);
		assertEquals(10, list.size());
	}
```

We could mock the list. get size, for more than one call by appending the `thenReturn`

```java
@Test
	public void letsMockListSizeWithMultipleReturnValues() {
		List list = mock(List.class);
		when(list.size()).thenReturn(10).thenReturn(20);
		assertEquals(10, list.size()); // First Call
		assertEquals(20, list.size()); // Second Call
	}
```

I could mock to return an specific value

```Java
@Test
public void letsMockListGet() {
	List<String> list = mock(List.class);
	when(list.get(0)).thenReturn("in28Minutes");
	assertEquals("in28Minutes", list.get(0));
	assertNull(list.get(1));
}
```


I could an `argument matcher` so that I dont need to pass anything to the method.

```Java
@Test
	public void letsMockListGetWithAny() {
		List<String> list = mock(List.class);
		Mockito.when(list.get(Mockito.anyInt())).thenReturn("in28Minutes");
		// If you are using argument matchers, all arguments
		// have to be provided by matchers.
		assertEquals("in28Minutes", list.get(0));
		assertEquals("in28Minutes", list.get(1));
	}
```

we could also mock to throw an execption as a result of calling a method:
```Java
@Test(expected = RuntimeException.class)
public void letsMockListGetToThrowException() {
	List<String> list = mock(List.class);
	when(list.get(Mockito.anyInt())).thenThrow(
			new RuntimeException("Something went wrong"));
	list.get(0);
}
```

## step 06 - Behaviour Driven Development - BDD
BDD : [BDD Reference](http://en.wikipedia.org/wiki/Behavior_Driven_Development)

It defines a clearly structured way of writing tests following three sections (Arrange, Act, Assert):
    given some preconditions (Arrange)
    when an action occurs (Act)
    then verify the output (Assert)

Let’s look at an example of a test body using traditional Mockito:

```Java			
when(phoneBookRepository.contains(momContactName))
  .thenReturn(false);

phoneBookService.register(momContactName, momPhoneNumber);

verify(phoneBookRepository)
  .insert(momContactName, momPhoneNumber);
```

Let’s see how that compares to BDDMockito:

```Java
given(phoneBookRepository.contains(momContactName))
  .willReturn(false);

phoneBookService.register(momContactName, momPhoneNumber);

then(phoneBookRepository)
  .should()
  .insert(momContactName, momPhoneNumber);
```


### Non BDD syntax
- `when` and `thenReturn`.
- `verify(mockclass, mockito.options).methodName()``
- `assertTrue(value, expected)`

### BDD syntax.
- `given(method).willReturn()`
- `then(mock).should(BDD_options).methodName()``
- `assertThat(value, is(expected))`


### Example - TodoBusinessImplMockitoTest.java

```Java
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
```

### Example - ListTest.java

```Java
@Test
	public void bddAliases_UsingGivenWillReturn() {
		List<String> list = mock(List.class);

		//given
		given(list.get(Mockito.anyInt())).willReturn("in28Minutes");

		//then
		assertThat("in28Minutes", is(list.get(0)));
		assertThat("in28Minutes", is(list.get(0)));
	}
```


## step 07 - Verify calls on Mocks.
we can check how many times a method has been call or if it has been called at all.

This is good way for the coverage.

### TodoBusinessImplMockitoTest.Java

Mocckito gives us the options to verify is a method been called, or never been call or how many times it has been called.

Check if a specific method has been called with the correct parameter.
This checks the *deleteTodo()* has been called  with the *Learn to Dance*
verify(todoService).deleteTodo("Learn to Dance")`


Check a specific method has NOT BEEN CALLED.
`verify(todoService, Mockito.never()).deleteTodo("Learn Spring MVC")``

Check a specific method has been called only 1 time
`verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");`

```Java
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
```

The learn how to verify methods by using **verify** . However, there are other options in BDD  that we could use to verify methods

The below syntax will do the same test as the previous methods:

```Java
		verify(todoServiceMock).deleteTodo( "Learn to Dance");
		then(todoServiceMock).should().deleteTodo("Learn to Dance");
```


```Java
		verify(todoServiceMock,Mockito.never()).deleteTodo("Learn Spring MVC");
		then(todoServiceMock).should(never()).deleteTodo("Learn Spring MVC");
```

```Java
		verify(todoServiceMock, Mockito.times(1)).deleteTodo("Learn to Dance");
		then(todoServiceMock).should(times(1)).deleteTodo("Learn to Dance");
```



## step 08 - Capturing arguments passed to a Mock
In the same test as before.
We can capture the arguments we are passing to the `deleteTodo()` method by using the **Argument Captor.**

to do this you need to

Declare the Argument Capture specifying what type of elements you want to Capture. E.g. String.
```Java
ArgumentCaptor<String> argumentCaptor = ArgumentCaptor
				.forClass(String.class);
```

Caputure the string or the object you want in the `verify`
```Java
verify(todoServiceMock).deleteTodo(stringArgumentCaptor.capture());
```

This will capture the value which we could assess if the expected.
```Java
assertEquals("Learn to Dance", stringArgumentCaptor.getValue());
```

#### TodoBusinessImplMockitoTest.java
See example not using the BDD style, this can be added to this java class.
- `when` and `thenReturn`.
- `verify(mockclass, mockito.options).methodName()``
- `assertTrue(value, expected)`

```Java
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
```

#### Same example using BDD:
- `given(method).willReturn()`
- `then(mock).should(BDD_options).methodName()``
- `assertThat(value, is(expected))`

```Java
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
    assertThat(stringArgumentCaptor.getValue(), is("Learn to Dance"));
}
```

### Dealing with multiple calls to the mock method.

Using the same example as before, but now we added a new book to the list, *Learn to Dance to delete too*, this will force the method *deleteTodo* to be called twice.

To test this with the Argument Capture class, we can the `getAllValues().size()` from the Argument capture class.

```Java
then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());
assertThat(stringArgumentCaptor.getAllValues().size(), is(2));
```

See full example here.

```Java
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
```



# Section 6. Mockito Advance

## Step 09 - Hamcrest Matchers
We can use *harmcrest* library can help us to make Junit test more readable.

There are many functions we could use with the assert, see an example:
For Arraylist:
- hasSize();
- hasItems(100, 101));

Using Matchers `everyItem`.
- everyItem(greaterThan(90)));
- everyItem(lessThan(200)));

For Strings:
- isEmptyString());
- isEmptyOrNullString();

For Arrays:
- arrayWithSize(3));
- arrayContaining(2, 4, 5, 6)
- arrayContainingInAnyOrder(2, 3, 1));

There are much more methods and matches you can use, check check org.hamcrest.Matchers.class


we need to add the dependency to pom if we want to use it.
```
<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-library</artifactId>
    <version>1.3</version>
    <scope>test</scope>
</dependency>  
```

### HamcrestMatcherTest.java

```Java
@Test
	public void mc_basicHamcrestMatchers() {

		// For array list
		List<Integer> scoreList = Arrays.asList(99, 100, 101,  105);

		assertThat(scoreList, hasSize(4));
		assertThat(scoreList, hasItems(99,105));

		//Array list: using Matchers
		assertThat(scoreList, everyItem(greaterThan(55)));
		assertThat(scoreList, everyItem(lessThan(190)));

		//String related assert
		assertThat("", isEmptyString());
		assertThat(null, isEmptyOrNullString());

		//Array
		Integer [] marks = {2, 4, 5, 6};
		assertThat(marks, arrayWithSize(4));
		assertThat(marks, arrayContaining(2, 4, 5, 6));
		assertThat(marks, arrayContainingInAnyOrder(6, 2));
	}
```



## Step 10 - Mockito Annotations
We are gong to convert existing classes with Mockito.
- Mockito Annotations
  - @Mock
  - @InjectMocks
  - @RunWith(MockitoJUnitRunner.class)
  - @Captor


### Example using TodoBusinessImplMockitoInjectMocksTest.java
we are going to convert class *TodoBusinessImplMockitoTest.java* to use mockito annotations.

#### @Mock annotation.
Creating the mock by using the @Mock annotation.

The mock was created by doing:
```Java
TodoService todoService = mock(TodoService.class);
```

Which is the same as doing:
```Java
@Mock
TodoService todoService;
```

We can now remove all the *TodoService todoService = mock(TodoService.class);* instances

#### @InjectMocks
Instead of us having to inject the mock into the class; Mockito can do it for us by using the  @InjectMocks

Injecting *todoService* mock into the *TodoBusinessImpl* class when we instantiate the class:  
```Java
TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
```

Injecting the mock by using InjectMocks
```Java
@InjectMocks
TodoBusinessImpl todoBusinessImpl;
```

We can now remove all the *TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService)* instances.

The test becomes more readable after removing all the unneeded instances.  
This will allow us to focus on the business logic and on the real code we want to test.

#### @Captor to implement ArgumentCaptor
We could also create the argument captor by using Mockito annotation; instead of creating it for every single test we want to use it.

```Java
ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
```  

Creating Argument Captor with mockito annotation:
```Java
@Captor
ArgumentCaptor<String> stringArgumentCaptor;
```

These two piece of code are the same; however, the mockito annotation makes us save from creating an *Argument Capture* in every single method.

*TodoBusinessImplMockitoWithMockitoAnnotationsTest.java* has been created to apply what I learned on this step.



### TodoBusinessImplMockitoInjectMocksTest.java final code
see below

```Java
@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessImplMockitoInjectMocksTest {
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
}
```


###  Step 11- Mockito Junit Rules

To run our tests we have used the Mockito Junit Runner,*RunWith(MockitoJUnitRunner.class)*.

The  makes sure all the mocks are created (checking @mock annotations.) and it auto-wire as per they need. This means the MockitoJunitRunner will inject the mocks into the Class we want by searching for @InjectMocks

The disadvantege of using Mockito Junit Runner is that we cannot use another runner on the same class.