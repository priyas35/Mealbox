package com.mealbox.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class FoodOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer foodOrderItemId;
	@ManyToOne
	@JoinColumn(name = "food_order_id")
	private FoodOrder foodOrderId;
	@ManyToOne
	@JoinColumn(name = "vendor_food_id")
	private VendorFood vendorFoodId;
	private Integer quantity;
}
