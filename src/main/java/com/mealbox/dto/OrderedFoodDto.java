package com.mealbox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedFoodDto {
	
	private String foodName;
	private Integer quantity;
	private String vendorName;
}
