package com.mealbox.dto;

import java.time.LocalDate;
import java.util.List;

import com.mealbox.common.MealboxEnum.OrderStatus;
import com.mealbox.common.MealboxEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsResponseDto {

	private Long foodOrderId;
	private Double totalAmount;
	private LocalDate orderDate;
	private PaymentType paymentType;
	private OrderStatus orderStatus;
	
	private List<OrderedFoodDto> orderedFood;

}
