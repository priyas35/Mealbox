package com.mealbox.service;

import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.dto.OrderRequestDto;
import com.mealbox.dto.OrderResponseDto;
import com.mealbox.exception.EmployeeNotFoundException;
import com.mealbox.exception.FoodNotFoundException;

public interface EmployeeService {

	LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException;
	OrderResponseDto placeOrder(OrderRequestDto orderRequestDto,Long employeeId) throws EmployeeNotFoundException, FoodNotFoundException;

}
