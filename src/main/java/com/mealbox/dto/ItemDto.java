package com.mealbox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

	private Integer foodId;
	private String foodName;
	private Double price;
	private Integer calories;
	private String status;
}
