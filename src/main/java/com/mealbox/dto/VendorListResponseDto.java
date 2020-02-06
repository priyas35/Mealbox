package com.mealbox.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendorListResponseDto {

	private Integer statusCode;
	private String message;
	private List<ItemCategoryDto> itemcategoryList;
}
