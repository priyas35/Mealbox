package com.mealbox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
	
	private Integer errorStatusCode;
	private String errorStatusMessage;

}
