package com.mealbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealbox.entity.VendorFood;

@Repository
public interface VendorFoodRepository extends JpaRepository<VendorFood, Integer> {

}
