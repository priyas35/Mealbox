package com.mealbox.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealbox.constant.Constant;
import com.mealbox.dto.ItemCategoryDto;
import com.mealbox.dto.ResponseDto;
import com.mealbox.dto.VendorDto;
import com.mealbox.dto.VendorFoodDto;
import com.mealbox.dto.VendorListResponseDto;
import com.mealbox.entity.Vendor;
import com.mealbox.exception.VendorNotFoundException;
import com.mealbox.service.VendorService;

/**
 * In this Rest controller we can handled the methods of get item list by
 * vendorId and get all vendors.
 * 
 * @author Govindasamy.C
 * @author PriyaDharshini S
 * @since 05-02-2020
 * @version V1.1
 *
 */
@RestController
@RequestMapping("/vendors")
@CrossOrigin
public class VendorController {
	private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

	@Autowired
	VendorService vendorService;

	/**
	 * Get the all vendor item list based on the vendorId
	 * 
	 * @param vendorId - Id of the vendor
	 * @return return the status code and message along with the vendor item list
	 *         details.
	 * @author Govindasamy.C
	 * @throws VendorNotFoundException - throws the VendorNotFoundException while
	 *                                 giving the wrong id and incorrect data.
	 * @since 05-02-2020
	 * 
	 */
	@GetMapping("/{vendorId}")
	public ResponseEntity<VendorListResponseDto> getItemListByVendorId(@PathVariable Integer vendorId)
			throws VendorNotFoundException {
		logger.info("get the vendor item list based on vendorId...");
		List<ItemCategoryDto> itemCategorylist = vendorService.getItemListByVendorId(vendorId);
		VendorListResponseDto vendorListResponseDto = new VendorListResponseDto();
		if (itemCategorylist.isEmpty()) {
			vendorListResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
			vendorListResponseDto.setMessage(Constant.NO_RECORDS_FOUND);
		} else {
			vendorListResponseDto.setStatusCode(HttpStatus.OK.value());
			vendorListResponseDto.setMessage(Constant.SUCCESS_MESSAGE);
			vendorListResponseDto.setItemcategoryList(itemCategorylist);
		}
		return new ResponseEntity<>(vendorListResponseDto, HttpStatus.OK);
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
	@PostMapping
	public ResponseEntity<ResponseDto> addVendor(@Valid @RequestBody VendorDto vendorDto) {
		logger.info("Add a new vendor based on the user input...");
		vendorService.addVendor(vendorDto);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(Constant.VENDOR_ADDED_SUCCESSFULLY);
		responseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the employee.
	 * 
	 * @return list of vendors. which has the list of vendors.
	 * @throws VendorNotFoundException it will throw the exception if the vendor is
	 *                                 not there.
	 */
	@GetMapping
	public ResponseEntity<List<Vendor>> getAllVendors() throws VendorNotFoundException {
		return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
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
	@PostMapping("/foods")
	public ResponseEntity<ResponseDto> addVendorFood(@Valid @RequestBody VendorFoodDto vendorFoodDto)
			throws VendorNotFoundException {
		logger.info("Add a new vendor based on the user input...");
		vendorService.addVendorFood(vendorFoodDto);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(Constant.VENDOR_FOOD_ADDED_SUCCESSFULLY);
		responseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}
