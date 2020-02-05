package com.mealbox.dto;

import javax.validation.constraints.NotNull;

import com.mealbox.common.MealboxEnum.FoodType;
import com.mealbox.constant.Constant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodDto {

	@NotNull(message = Constant.VENDOR_FOOD_NAME_NOT_NULL_MESSAGE)
	private String foodName;

	@NotNull(message = Constant.VENDOR_FOOD_TYPE_NOT_NULL_MESSAGE)
	private FoodType foodType;

	private Double rate;
}
