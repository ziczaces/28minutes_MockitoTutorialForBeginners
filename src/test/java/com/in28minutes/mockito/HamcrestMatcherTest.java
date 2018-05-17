package com.in28minutes.mockito;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Every.everyItem;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import sun.rmi.server.InactiveGroupException;

public class HamcrestMatcherTest {

	@Test
	public void basicHamcrestMatchers() {
		List<Integer> scores = Arrays.asList(99, 100, 101, 105);
		assertThat(scores, hasSize(4));
		assertThat(scores, hasItems(100, 101));
		assertThat(scores, everyItem(greaterThan(90)));
		assertThat(scores, everyItem(lessThan(200)));

		// String
		assertThat("", isEmptyString());
		assertThat(null, isEmptyOrNullString());

		// Array
		Integer[] marks = { 1, 2, 3 };

		assertThat(marks, arrayWithSize(3));
		assertThat(marks, arrayContainingInAnyOrder(2, 3, 1));

	}

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
}