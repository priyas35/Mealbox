package com.mealbox.service;

import java.util.List;

import com.mealbox.dto.ItemCategoryDto;

public interface VendorService {

	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId);
}
