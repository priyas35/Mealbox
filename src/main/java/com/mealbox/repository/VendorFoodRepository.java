package com.mealbox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mealbox.entity.Food;
import com.mealbox.entity.Vendor;
import com.mealbox.entity.VendorFood;

@Repository
public interface VendorFoodRepository extends JpaRepository<VendorFood, Integer> {

	Optional<VendorFood> findByVendorIdAndFoodId(Vendor vendor, Food food);

	List<VendorFood> findByVendorId(Vendor vendor);
}
