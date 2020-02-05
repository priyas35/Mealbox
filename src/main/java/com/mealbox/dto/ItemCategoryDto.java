package com.mealbox.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCategoryDto {

	private String categoryName;
	private List<ItemDto> itemList;
}
