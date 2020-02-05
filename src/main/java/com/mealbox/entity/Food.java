package com.mealbox.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mealbox.common.MealboxEnum.FoodType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Food {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer foodId;
	private String foodName;
	private Double price;
	private Integer calories;
	@Enumerated(EnumType.STRING)
	private FoodType foodType;
}
