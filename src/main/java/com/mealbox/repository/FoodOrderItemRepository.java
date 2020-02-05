package com.mealbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealbox.entity.FoodOrder;
import com.mealbox.entity.FoodOrderItem;

@Repository
public interface FoodOrderItemRepository extends JpaRepository<FoodOrderItem, Integer> {
	
	List<FoodOrderItem> findByFoodOrderId(FoodOrder foodOrderId);

}
