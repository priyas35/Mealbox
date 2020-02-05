package com.mealbox.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealbox.common.MealboxEnum.FoodType;
import com.mealbox.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

	Optional<Food> findByFoodNameAndFoodType(String foodName, FoodType foodType);
}
