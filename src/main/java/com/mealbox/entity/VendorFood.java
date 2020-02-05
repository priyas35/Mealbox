package com.mealbox.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mealbox.common.MealboxEnum.FoodStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class VendorFood {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer vendorFoodId;
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private Vendor vendorId;
	@ManyToOne
	@JoinColumn(name = "food_id")
	private Food foodId;
	@Enumerated(EnumType.STRING)
	private FoodStatus status;
}
