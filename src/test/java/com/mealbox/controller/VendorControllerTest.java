package com.mealbox.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mealbox.common.MealboxEnum;
import com.mealbox.constant.Constant;
import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.dto.ItemDto;
import com.mealbox.dto.VendorListResponseDto;
import com.mealbox.entity.Vendor;
import com.mealbox.exception.VendorNotFoundException;
import com.mealbox.service.VendorService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VendorControllerTest {

	@InjectMocks
	VendorController vendorController;

	@Mock
	VendorService vendorService;

	List<ItemCategoryDto> itemCategoryList = new ArrayList<>();
	List<ItemDto> itemDtos = new ArrayList<>();
	ItemDto itemDto = new ItemDto();
	ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
	Vendor vendor = new Vendor();
	List<Vendor> vendors = new ArrayList<>();

	@Before
	public void init() {

		itemDto.setFoodId(1);
		itemDto.setFoodName("Veg Meals");

		itemDtos.add(itemDto);
		itemCategoryDto.setItemList(itemDtos);
		itemCategoryDto.setCategoryName(MealboxEnum.FoodType.VEG);

		itemCategoryList.add(itemCategoryDto);
		vendor.setVendorId(1);
		vendors.add(vendor);
	}
	
	

	@Test
	public void testGetItemListByVendorId() throws VendorNotFoundException {
		when(vendorService.getItemListByVendorId(1)).thenReturn(itemCategoryList);
		ResponseEntity<VendorListResponseDto> response = vendorController.getItemListByVendorId(1);
		assertThat(response.getBody().getItemcategoryList()).hasSize(1);
	}

	@Test
	public void testGetItemListByVendorIdForNegative() throws VendorNotFoundException {
		List<ItemCategoryDto> itemCategoryListEmpty = new ArrayList<>();

		when(vendorService.getItemListByVendorId(1)).thenReturn(itemCategoryListEmpty);
		ResponseEntity<VendorListResponseDto> response = vendorController.getItemListByVendorId(1);
		assertEquals(response.getBody().getMessage(), Constant.NO_RECORDS_FOUND);
	}

	@Test
	public void testGetAllVendors() throws VendorNotFoundException {
		Mockito.when(vendorService.getAllVendors()).thenReturn(vendors);
		ResponseEntity<List<Vendor>> actual = vendorController.getAllVendors();
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	

}
