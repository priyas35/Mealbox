package com.mealbox.service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealbox.common.MealboxEnum;
import com.mealbox.common.MealboxEnum.FoodType;
import com.mealbox.common.converter.FoodConverter;
import com.mealbox.constant.Constant;
import com.mealbox.dto.FoodDto;
import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.dto.ItemDto;
import com.mealbox.dto.VendorDto;
import com.mealbox.dto.VendorFoodDto;
import com.mealbox.entity.Food;
import com.mealbox.entity.Vendor;
import com.mealbox.entity.VendorFood;
import com.mealbox.exception.VendorNotFoundException;
import com.mealbox.repository.FoodRepository;
import com.mealbox.repository.VendorFoodRepository;
import com.mealbox.repository.VendorRepository;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Transactional
public class VendorServiceImpl implements VendorService {

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	VendorFoodRepository vendorFoodRepository;

	@Autowired
	FoodRepository foodRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the employee.
	 * 
	 * @return list of vendors. which has the list of vendors.
	 * @throws VendorNotFoundException it will throw the exception if the vendor is
	 *                                 not there.
	 */
	@Override
	public List<Vendor> getAllVendors() throws VendorNotFoundException {
		List<Vendor> vendors = vendorRepository.findAll();
		if (vendors.isEmpty()) {
			log.info("Entering into VendorServiceImpl:" + Constant.VENDOR_NOT_FOUND);
			throw new VendorNotFoundException(Constant.VENDOR_NOT_FOUND);
		} else {
			log.info("Entering into VendorServiceImpl:getting list of vendors");
			return vendors;
		}
	}

	@Override
	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId) throws VendorNotFoundException {
		log.info("Vendor Service - get the vendor item list based on vendorId...");
		// Check the Vendor is present or not by giving vendorId.
		Optional<Vendor> vendor = vendorRepository.findById(vendorId);
		if (!vendor.isPresent()) {
			throw new VendorNotFoundException(Constant.VENDOR_NOT_FOUND);
		}

		// Get the vendor food item list
		List<VendorFood> vendorFoods = vendorFoodRepository.findByVendorId(vendor.get());

		// Filter the foods from the vendor food list
		List<Food> foods = vendorFoods.stream().map(VendorFood::getFoodId).collect(Collectors.toList());

		List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();

		// Check the item category's and set the items by category wise
		List<FoodType> foodTypes = new ArrayList<>(EnumSet.allOf(MealboxEnum.FoodType.class));
		foodTypes.forEach(foodType -> {
			ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
			itemCategoryDto.setCategoryName(foodType);

			// Filter the food by category wise and mapping the food entity values to dto.
			List<ItemDto> itemList = foods.stream().filter(food -> food.getFoodType().equals(foodType))
					.map(FoodConverter::convertEntityToDto).collect(Collectors.toList());

			// Set the itemList to response dto.
			itemCategoryDto.setItemList(itemList);
			itemCategoryDtos.add(itemCategoryDto);
		});

		return itemCategoryDtos;
	}

	/**
	 * Add a new vendor based on the user input.
	 * 
	 * @param vendorDto - vendor details for adding a new vendor
	 * @return details of the response with status code and message
	 * @author Govindasamy.C
	 * @since 05-02-2020
	 * 
	 */
	@Override
	public void addVendor(VendorDto vendorDto) {
		log.info("Add a new vendor based on the user input...");
		Vendor vendor = new Vendor();
		BeanUtils.copyProperties(vendorDto, vendor);
		vendorRepository.save(vendor);
	}

	/**
	 * Add a new vendor food based on the vendor.
	 * 
	 * @param vendorDto - vendor details for adding a new vendor
	 * @return details of the response with status code and message
	 * @author Govindasamy.C
	 * @throws VendorNotFoundException
	 * @since 05-02-2020
	 * 
	 */
	@Override
	public void addVendorFood(VendorFoodDto vendorFoodDto) throws VendorNotFoundException {
		log.info("Add a new vendor food based on the vendorId...");
		// Check the Vendor is present or not by giving vendorId.
		Optional<Vendor> vendor = vendorRepository.findById(vendorFoodDto.getVendorId());
		if (!vendor.isPresent()) {
			throw new VendorNotFoundException(Constant.VENDOR_NOT_FOUND);
		}

		// Get the food item list from user input.
		List<FoodDto> foodDtos = vendorFoodDto.getFoodItemList();

		// Filter and map the values of the vendor food object.
		List<VendorFood> vendorFoods = foodDtos.stream().map(foodDto -> convertDtoToEntity(foodDto, vendor.get()))
				.collect(Collectors.toList());

		// Save all the food items for the vendor.
		vendorFoodRepository.saveAll(vendorFoods);
	}

	/**
	 * Convert the dto values to vendor food entity
	 * 
	 * @param foodDto - details of the food such as foodName and rate
	 * @param vendor  - details of the vendor object
	 * @return vendorFood object after check and set the values of the user
	 * @author Govindasamy.C
	 * @since 05-02-2020
	 */
	private VendorFood convertDtoToEntity(FoodDto foodDto, Vendor vendor) {
		log.info("convert the food details to vendorfood...");
		VendorFood vendorFood = new VendorFood();

		Optional<Food> food = foodRepository.findByFoodNameAndFoodType(foodDto.getFoodName(), foodDto.getFoodType());
		if (!food.isPresent()) {
			Food insertFood = new Food();
			insertFood.setFoodName(foodDto.getFoodName());
			insertFood.setPrice(foodDto.getRate());
			insertFood.setFoodType(foodDto.getFoodType());
			foodRepository.save(insertFood);

			vendorFood.setFoodId(insertFood);
		} else {
			vendorFood.setFoodId(food.get());
		}

		vendorFood.setStatus(MealboxEnum.FoodStatus.AVAILABLE);
		vendorFood.setVendorId(vendor);

		return vendorFood;

	}
}
