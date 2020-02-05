package com.mealbox.service;

import java.util.List;

import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.dto.OrderDetailsResponseDto;
import com.mealbox.dto.OrderRequestDto;
import com.mealbox.dto.OrderResponseDto;
import com.mealbox.exception.EmployeeNotFoundException;
import com.mealbox.exception.FoodNotFoundException;
import com.mealbox.exception.OrderNotFoundException;

public interface EmployeeService {

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the employee.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message and statusCode.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 * 
	 */
	LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException;
	OrderResponseDto placeOrder(OrderRequestDto orderRequestDto,Long employeeId) throws EmployeeNotFoundException, FoodNotFoundException;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will get particular order details by passing
	 *        the employeeId.
	 * @param employeeId.This is the id of the employee.
	 * @return OrderDetailsResponseDto which has orderDetails.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 * @throws OrderNotFoundException    it will throw the exception if the order is
	 *                                   not placed.
	 * 
	 */
	List<OrderDetailsResponseDto> getMyOrder(Long employeeId) throws EmployeeNotFoundException, OrderNotFoundException;

}
