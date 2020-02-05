package com.mealbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealbox.entity.FoodOrderItem;

@Repository
public interface FoodOrderItemRepository extends JpaRepository<FoodOrderItem, Integer> {

}
