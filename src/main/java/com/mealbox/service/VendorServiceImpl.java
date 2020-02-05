package com.mealbox.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mealbox.dto.ItemCategoryDto;

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

	@Override
	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
