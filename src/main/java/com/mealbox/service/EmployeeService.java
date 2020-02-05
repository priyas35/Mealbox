package com.mealbox.service;

import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.exception.EmployeeNotFoundException;

public interface EmployeeService {

	LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException;

}
