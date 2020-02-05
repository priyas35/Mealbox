package com.mealbox.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.mealbox.constant.Constant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendorFoodDto {

	@NotNull(message = Constant.VENDOR_ID_NOT_NULL_MESSAGE)
	private Integer vendorId;
	
   private List<FoodDto> foodItemList;
}
