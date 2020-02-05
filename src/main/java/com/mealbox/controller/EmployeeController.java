package com.mealbox.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.dto.OrderDetailsResponseDto;
import com.mealbox.exception.EmployeeNotFoundException;
import com.mealbox.exception.OrderNotFoundException;
import com.mealbox.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employees")
@CrossOrigin
@Slf4j
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the employee.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message and statusCode.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 * 
	 */
	@PostMapping
	public ResponseEntity<LoginResponseDto> authenticateEmployee(@Valid @RequestBody LoginRequestDto loginDto)
			throws EmployeeNotFoundException {
		LoginResponseDto loginResponseDto = employeeService.authenticateEmployee(loginDto);
		log.info("Authentication Successful");
		loginResponseDto.setMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.AUTHENTICATION_SUCCESSFUL_CODE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

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
	@GetMapping("/{employeeId}/orders")
	public ResponseEntity<List<OrderDetailsResponseDto>> getOrderDetails(@PathVariable Long employeeId)
			throws EmployeeNotFoundException, OrderNotFoundException {
		if (employeeId == null) {
			log.info("Entering into EmployeeController: employeeId not entered");
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		} else {
			log.info("Entering into EmployeeController: getting order details");
			return new ResponseEntity<>(employeeService.getMyOrder(employeeId), HttpStatus.OK);
		}
	}

}
