package com.mealbox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto extends ResponseDto{
	private String role;
	private Long employeeId;
	private String employeeName;

}
