package com.mealbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealbox.constant.Constant;
import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.entity.Vendor;
import com.mealbox.exception.VendorNotFoundException;
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
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	VendorRepository vendorRepository;

	@Override
	public List<ItemCategoryDto> getItemListByVendorId(Integer vendorId) {
		return null;
	}

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
		if(vendors.isEmpty()) {
			log.info("Entering into VendorServiceImpl:"+Constant.VENDOR_NOT_FOUND);
			throw new VendorNotFoundException(Constant.VENDOR_NOT_FOUND);
		}else {
			log.info("Entering into VendorServiceImpl:getting list of vendors");
			return vendors;
		}
	}
}
