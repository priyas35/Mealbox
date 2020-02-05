package com.mealbox.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealbox.constant.Constant;
import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.entity.Employee;
import com.mealbox.exception.EmployeeNotFoundException;
import com.mealbox.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-01-28. This method will authenticate the employee.
	 * @param loginRequestDto - details of the user login
	 * 
	 * @return LoginResponseDto which has status message and statusCode.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 */
	@Override
	public LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException {
		Optional<Employee> employee = employeeRepository.findByEmployeeIdAndPassword(loginRequestDto.getEmployeeId(),
				loginRequestDto.getPassword());
		if (!employee.isPresent()) {
			log.error("employee not found");
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		} else {
			LoginResponseDto loginResponseDto = new LoginResponseDto();
			loginResponseDto.setRole(employee.get().getRole().toString());
			loginResponseDto.setEmployeeId(employee.get().getEmployeeId());
			loginResponseDto.setEmployeeName(employee.get().getEmployeeName());
			log.info("Authentication Successful");
			return loginResponseDto;
		}
	}

}
