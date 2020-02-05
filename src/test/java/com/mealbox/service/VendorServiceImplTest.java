package com.mealbox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mealbox.common.MealboxEnum;
import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.entity.Food;
import com.mealbox.entity.Vendor;
import com.mealbox.entity.VendorFood;
import com.mealbox.exception.VendorNotFoundException;
import com.mealbox.repository.VendorFoodRepository;
import com.mealbox.repository.VendorRepository;

@RunWith(MockitoJUnitRunner.class)
public class VendorServiceImplTest {

	@InjectMocks
	VendorServiceImpl vendorServiceImpl;

	@Mock
	VendorRepository vendorRepository;

	@Mock
	VendorFoodRepository vendorFoodRepository;

	Vendor vendor = new Vendor();
	Food food = new Food();
	VendorFood vendorFood = new VendorFood();
	List<Vendor> vendors = new ArrayList<>();

	@Before
	public void init() {
		vendor.setVendorId(1);
		vendors.add(vendor);
		food.setFoodId(1);
		food.setFoodType(MealboxEnum.FoodType.VEG);

		vendorFood.setVendorId(vendor);
		vendorFood.setFoodId(food);
	}

	@Test
	public void testGetItemListByVendorId() throws VendorNotFoundException {
		List<VendorFood> vendorFoods = new ArrayList<>();
		vendorFoods.add(vendorFood);

		when(vendorRepository.findById(1)).thenReturn(Optional.of(vendor));
		when(vendorFoodRepository.findByVendorId(vendor)).thenReturn(vendorFoods);

		List<ItemCategoryDto> response = vendorServiceImpl.getItemListByVendorId(1);
		assertThat(response).hasSize(2);
	}

	@Test(expected = VendorNotFoundException.class)
	public void testGetItemListByVendorIdForVendorNotFound() throws VendorNotFoundException {
		when(vendorRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		vendorServiceImpl.getItemListByVendorId(1);
	}

	@Test
	public void testGetAllVendors() throws VendorNotFoundException {
		Mockito.when(vendorRepository.findAll()).thenReturn(vendors);
		List<Vendor> actual = vendorServiceImpl.getAllVendors();
		assertEquals(1, actual.size());
	}

	@Test(expected = VendorNotFoundException.class)
	public void testGetAllVendorsForVendorNotFoundException() throws VendorNotFoundException {
		vendors = new ArrayList<>();
		Mockito.when(vendorRepository.findAll()).thenReturn(vendors);
		vendorServiceImpl.getAllVendors();
	}

}
