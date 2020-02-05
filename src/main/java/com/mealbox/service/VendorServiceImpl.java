package com.mealbox.service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealbox.common.MealboxEnum;
import com.mealbox.common.MealboxEnum.FoodType;
import com.mealbox.common.converter.FoodConverter;
import com.mealbox.constant.Constant;
import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.dto.ItemDto;
import com.mealbox.entity.Food;
import com.mealbox.entity.Vendor;
import com.mealbox.entity.VendorFood;
import com.mealbox.exception.VendorNotFoundException;
import com.mealbox.repository.VendorFoodRepository;
import com.mealbox.repository.VendorRepository;

/**
 * In this VendorServiceImpl Class we can handled the methods of get item list
 * by vendorId
 * 
 * @author Govindasamy.C
 * @since 05-02-2020
 * @version V1.1
 *
 */
@Service
public class VendorServiceImpl implements VendorService {
	private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	VendorFoodRepository vendorFoodRepository;

	@Override
	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId) throws VendorNotFoundException {
		logger.info("Vendor Service - get the vendor item list based on vendorId...");
		// Check the Vendor is present or not by giving vendorId.
		Optional<Vendor> vendor = vendorRepository.findById(vendorId);
		if (!vendor.isPresent()) {
			throw new VendorNotFoundException(Constant.VENDOR_NOT_FOUND);
		}

		// Get the vendor food item list
		List<VendorFood> vendorFoods = vendorFoodRepository.findByVendorId(vendor.get());

		// Filter the foods from the vendor food list
		List<Food> foods = vendorFoods.stream()
				.map(vendorFood -> vendorFood.getFoodId())
				.collect(Collectors.toList());

		List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();

		// Check the item category's and set the items by category wise
		List<FoodType> foodTypes = new ArrayList<>(EnumSet.allOf(MealboxEnum.FoodType.class));
		foodTypes.forEach(foodType -> {
			ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
			itemCategoryDto.setCategoryName(foodType);

			// Filter the food by category wise and mapping the food entity values to dto.
			List<ItemDto> itemList = foods.stream().filter(food -> food.getFoodType().equals(foodType))
					.map(food -> FoodConverter.convertEntityToDto(food)).collect(Collectors.toList());
			
			// Set the itemList to response dto.
			itemCategoryDto.setItemList(itemList);
			itemCategoryDtos.add(itemCategoryDto);
		});

		return itemCategoryDtos;
	}

}
