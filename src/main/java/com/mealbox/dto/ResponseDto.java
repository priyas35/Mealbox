package com.mealbox.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {

	private Integer statusCode;
	private String message;
}
