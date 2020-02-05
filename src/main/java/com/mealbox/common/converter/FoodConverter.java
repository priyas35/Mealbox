package com.mealbox.common.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.mealbox.dto.ItemDto;
import com.mealbox.entity.Food;

/**
 * Convert the Food entity or dto values to respective object. Mainly we are
 * using this class for conversion purpose.
 * 
 * @author Govindasamy.C
 * @since 05-02-2020
 * @version V1.1
 *
 */
public class FoodConverter {
	private static final Logger logger = LoggerFactory.getLogger(FoodConverter.class);

	private FoodConverter() {
	}

	/**
	 * Convert the food detail object to item dto object.
	 * 
	 * @param food - details of the food object
	 * @return the converted food details to item dto for response purpose.
	 * @author Govindasamy.C
	 * @since 05-02-2020
	 */
	public static ItemDto convertEntityToDto(Food food) {
		logger.info("Convert the food detail object to item dto object...");
		ItemDto itemDto = new ItemDto();
		BeanUtils.copyProperties(food, itemDto);
		return itemDto;
	}
}
