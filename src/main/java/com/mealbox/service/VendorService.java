package com.mealbox.service;

import java.util.List;

import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.dto.VendorDto;
import com.mealbox.dto.VendorFoodDto;
import com.mealbox.entity.Vendor;
import com.mealbox.exception.VendorNotFoundException;

public interface VendorService {

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the employee.
	 * 
	 * @return list of vendors. which has the list of vendors.
	 * @throws VendorNotFoundException it will throw the exception if the vendor
	 *                                   is not there.
	 */
	List<Vendor> getAllVendors() throws VendorNotFoundException;
	
	
	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId) throws VendorNotFoundException;
	
	public void addVendor(VendorDto vendorDto);
	
	public void addVendorFood(VendorFoodDto vendorFoodDto) throws VendorNotFoundException;

}
