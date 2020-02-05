package com.mealbox.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mealbox.constant.Constant;
import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.exception.EmployeeNotFoundException;
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
	 * @since 2020-01-28. This method will authenticate the employee.
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

}
