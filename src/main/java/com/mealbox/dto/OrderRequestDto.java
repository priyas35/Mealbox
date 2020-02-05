package com.mealbox.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mealbox.common.MealboxEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

	@NotNull(message="Mode of payment cannot be null")
	private PaymentType paymentType;
	
	 @NotNull(message="vendorId cannot be null")
	private Integer vendorId;
	
	@NotEmpty(message="foodList cannot be empty")
	List<FoodDetail> foodList;
	
	@NotNull(message="totalAmount cannot be null")
	private Double totalAmount;
}
