package com.mealbox.dto;

import java.util.List;

import com.mealbox.common.MealboxEnum.FoodType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCategoryDto {

	private FoodType categoryName;
	private List<ItemDto> itemList;
}
