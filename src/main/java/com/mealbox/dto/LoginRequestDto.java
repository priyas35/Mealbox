package com.mealbox.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	
	@NotNull(message = "employeeId is required")
	private Long employeeId;
	@NotNull(message = "password is required")
	private String password;
	

}
