package com.mealbox.service;

import java.util.List;

import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.exception.VendorNotFoundException;

public interface VendorService {

	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId) throws VendorNotFoundException;
}
