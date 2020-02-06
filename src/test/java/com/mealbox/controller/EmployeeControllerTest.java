package com.mealbox.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mealbox.common.MealboxEnum.OrderStatus;
import com.mealbox.common.MealboxEnum.Role;
import com.mealbox.constant.Constant;
import com.mealbox.dto.LoginRequestDto;
import com.mealbox.dto.LoginResponseDto;
import com.mealbox.dto.OrderDetailsResponseDto;
import com.mealbox.dto.OrderRequestDto;
import com.mealbox.dto.OrderResponseDto;
import com.mealbox.dto.OrderedFoodDto;
import com.mealbox.entity.Employee;
import com.mealbox.entity.Food;
import com.mealbox.entity.FoodOrder;
import com.mealbox.entity.FoodOrderItem;
import com.mealbox.entity.Vendor;
import com.mealbox.entity.VendorFood;
import com.mealbox.exception.EmployeeNotFoundException;
import com.mealbox.exception.FoodNotFoundException;
import com.mealbox.exception.OrderNotFoundException;
import com.mealbox.service.EmployeeService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeControllerTest {

	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeService employeeService;

	Employee employee = new Employee();
	FoodOrder foodOrder = new FoodOrder();
	FoodOrderItem foodOrderItem = new FoodOrderItem();
	List<FoodOrder> foodOrders = new ArrayList<>();
	List<FoodOrderItem> foodOrderItems = new ArrayList<>();
	Vendor vendor = new Vendor();
	Food food = new Food();
	VendorFood vendorFood = new VendorFood();
	OrderDetailsResponseDto orderDetailsResponseDto = new OrderDetailsResponseDto();
	List<OrderDetailsResponseDto> orderDetails = new ArrayList<>();
	OrderedFoodDto orderedFoodDto = new OrderedFoodDto();
	List<OrderedFoodDto> orderedFoods = new ArrayList<>();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	LoginRequestDto loginRequestDto = new LoginRequestDto();
	OrderRequestDto orderRequestDto = new OrderRequestDto();
	OrderResponseDto orderResponseDto = new OrderResponseDto();

	@Before
	public void init() {

		employee.setEmployeeId(1L);
		employee.setPassword("priya");
		employee.setEmployeeName("sri");
		employee.setRole(Role.EMPLOYEE);
		food.setFoodId(1);
		food.setFoodName("biryani");

		vendor.setVendorId(1);
		vendor.setVendorName("kfc");
		vendorFood.setVendorFoodId(1);
		vendorFood.setVendorId(vendor);
		vendorFood.setFoodId(food);
		foodOrder.setFoodOrderId(1L);
		foodOrder.setEmployeeId(employee);
		foodOrderItem.setVendorFoodId(vendorFood);
		foodOrderItem.setFoodOrderId(foodOrder);
		foodOrderItem.setFoodOrderItemId(1);
		foodOrders.add(foodOrder);
		foodOrderItems.add(foodOrderItem);

		orderedFoodDto.setFoodName("Biryani");
		orderedFoodDto.setVendorName("kfc");
		orderedFoodDto.setQuantity(1);
		orderedFoods.add(orderedFoodDto);
		orderDetailsResponseDto.setFoodOrderId(1L);
		orderDetailsResponseDto.setOrderStatus(OrderStatus.ORDERED);
		orderDetailsResponseDto.setOrderedFood(orderedFoods);
		orderDetails.add(orderDetailsResponseDto);

		loginRequestDto.setEmployeeId(1L);
		loginRequestDto.setPassword("priya");
		loginResponseDto.setEmployeeId(1L);
		loginResponseDto.setEmployeeName("sri");

	}

	@Test
	public void testGetMyOrders() throws EmployeeNotFoundException, OrderNotFoundException {
		Mockito.when(employeeService.getMyOrder(1L)).thenReturn(orderDetails);
		ResponseEntity<List<OrderDetailsResponseDto>> actual = employeeController.getOrderDetails(1L);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testGetMyOrdersForEmployeeNotFoundException() throws EmployeeNotFoundException, OrderNotFoundException {
		employee.setEmployeeId(null);
		employeeController.getOrderDetails(null);

	}

	@Test
	public void testAuthenticateEmployee() throws EmployeeNotFoundException {
		loginResponseDto.setMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.AUTHENTICATION_SUCCESSFUL_CODE);
		Mockito.when(employeeService.authenticateEmployee(loginRequestDto)).thenReturn(loginResponseDto);
		ResponseEntity<LoginResponseDto> result = employeeController.authenticateEmployee(loginRequestDto);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}
	
	@Test
	public void testPlaceOrder() throws EmployeeNotFoundException, FoodNotFoundException {
		employee.setEmployeeId(1L);
	Mockito.when(employeeService.placeOrder(orderRequestDto, 1L)).thenReturn(orderResponseDto);
	ResponseEntity<OrderResponseDto> response=employeeController.placeOrder(orderRequestDto, 1L);
	assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
