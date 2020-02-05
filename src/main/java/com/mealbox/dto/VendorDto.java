package com.mealbox.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.mealbox.constant.Constant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendorDto {

	@NotNull(message = Constant.VENDOR_NAME_NOT_NULL_MESSAGE)
	private String vendorName;
	
    @Min(value = 1, message = "rating can't be less than 1")
	private Double rating;
}
