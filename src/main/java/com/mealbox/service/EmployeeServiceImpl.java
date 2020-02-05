package com.mealbox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealbox.constant.Constant;
import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.dto.OrderDetailsResponseDto;
import com.mealbox.dto.OrderedFoodDto;
import com.mealbox.entity.Employee;
import com.mealbox.entity.FoodOrder;
import com.mealbox.entity.FoodOrderItem;
import com.mealbox.exception.EmployeeNotFoundException;
import com.mealbox.exception.OrderNotFoundException;
import com.mealbox.repository.EmployeeRepository;
import com.mealbox.repository.FoodOrderItemRepository;
import com.mealbox.repository.FoodOrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	FoodOrderRepository foodOrderRepository;

	@Autowired
	FoodOrderItemRepository foodOrderItemRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-05. This method will authenticate the employee.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message and statusCode.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 * 
	 */
	@Override
	public LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException {
		Optional<Employee> employee = employeeRepository.findByEmployeeIdAndPassword(loginRequestDto.getEmployeeId(),
				loginRequestDto.getPassword());
		if (!employee.isPresent()) {
			log.error("Entering into EmployeeServiceImpl:" + Constant.EMPLOYEE_NOT_FOUND);
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
	@Override
	public List<OrderDetailsResponseDto> getMyOrder(Long employeeId)
			throws EmployeeNotFoundException, OrderNotFoundException {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (!employee.isPresent()) {
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		}
		List<FoodOrder> foodOrder = foodOrderRepository.findByEmployeeId(employee.get());
		if (foodOrder.isEmpty()) {
			throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
		}
		List<OrderDetailsResponseDto> myOrders = new ArrayList<>();
		foodOrder.forEach(foodOrders -> {
		
			List<FoodOrderItem> foodOrderItems = foodOrderItemRepository.findByFoodOrderId(foodOrders);
			OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();
			BeanUtils.copyProperties(foodOrders, orderDetailsResponseDto);
			List<OrderedFoodDto> foodOrderedList = new ArrayList<>();
			foodOrderItems.forEach(orderedFoods -> {
				OrderedFoodDto orderedFoodDto = new OrderedFoodDto();
				orderedFoodDto.setFoodName(orderedFoods.getVendorFoodId().getFoodId().getFoodName());
			    orderedFoodDto.setVendorName(orderedFoods.getVendorFoodId().getVendorId().getVendorName());
				orderedFoodDto.setQuantity(orderedFoods.getQuantity());
				foodOrderedList.add(orderedFoodDto);
			});
			orderDetailsResponseDto.setOrderedFood(foodOrderedList);
			myOrders.add(orderDetailsResponseDto);
		});

		return myOrders;

	}

}
