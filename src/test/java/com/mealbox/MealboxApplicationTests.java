package com.mealbox;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MealboxApplicationTests {

	@Test
	public void applicationTest() {
		MealboxApplication.main(new String[] {});
		assertTrue(true);
	}

}
