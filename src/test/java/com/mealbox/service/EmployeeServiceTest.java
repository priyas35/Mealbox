package com.mealbox.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mealbox.common.MealboxEnum.OrderStatus;
import com.mealbox.common.MealboxEnum.Role;
import com.mealbox.dto.FoodDetail;
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
import com.mealbox.exception.VendorNotFoundException;
import com.mealbox.repository.EmployeeRepository;
import com.mealbox.repository.FoodOrderItemRepository;
import com.mealbox.repository.FoodOrderRepository;
import com.mealbox.repository.FoodRepository;
import com.mealbox.repository.VendorFoodRepository;
import com.mealbox.repository.VendorRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeServiceTest {

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;

	@Mock
	EmployeeRepository employeeRepository;

	@Mock
	FoodOrderRepository foodOrderRepository;

	@Mock
	FoodOrderItemRepository foodOrderItemRepository;
	
	@Mock
	VendorRepository vendorRepository;
	
	@Mock
	FoodRepository foodRepository;
	
	@Mock
	VendorFoodRepository vendorFoodRepository;

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
	FoodDetail foodDetail= new FoodDetail();
	

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
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(foodOrderRepository.findByEmployeeId(employee)).thenReturn(foodOrders);
		Mockito.when(foodOrderItemRepository.findByFoodOrderId(foodOrder)).thenReturn(foodOrderItems);
		List<OrderDetailsResponseDto> actual = employeeServiceImpl.getMyOrder(1L);
		assertEquals(1, actual.size());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testGetMyOrdersForEmployeeNotFoundException() throws EmployeeNotFoundException, OrderNotFoundException {
		Mockito.when(employeeRepository.findById(111L)).thenReturn(Optional.of(employee));
		employeeServiceImpl.getMyOrder(1L);
	}

	@Test(expected = OrderNotFoundException.class)
	public void testGetMyOrdersForOrderNotFoundException() throws EmployeeNotFoundException, OrderNotFoundException {
		foodOrders = new ArrayList<>();
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(foodOrderRepository.findByEmployeeId(employee)).thenReturn(foodOrders);
		employeeServiceImpl.getMyOrder(1L);
	}

	@Test
	public void testAuthenticateEmployeePositive() throws EmployeeNotFoundException {
		Mockito.when(employeeRepository.findByEmployeeIdAndPassword(1L, "priya")).thenReturn(Optional.of(employee));
		LoginResponseDto result = employeeServiceImpl.authenticateEmployee(loginRequestDto);
		assertEquals("sri", result.getEmployeeName());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testAuthenticateEmployeeForEmployeeNotFound() throws EmployeeNotFoundException {
		Mockito.when(employeeRepository.findByEmployeeIdAndPassword(2L, "sri")).thenReturn(Optional.of(employee));
		employeeServiceImpl.authenticateEmployee(loginRequestDto);

	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void testPlaceOrderNoEmployee() throws EmployeeNotFoundException, FoodNotFoundException {
	Mockito.when(employeeRepository.findByEmployeeId(11L)).thenReturn(Optional.of(employee));
	employeeServiceImpl. placeOrder( orderRequestDto, 1L);
	}
	
	@Test(expected = VendorNotFoundException.class)
	public void convertToFoodOrderItem() throws EmployeeNotFoundException, FoodNotFoundException, VendorNotFoundException {
	Mockito.when(vendorRepository.findById(111)).thenReturn(Optional.of(vendor));
	employeeServiceImpl.convertToFoodOrderItem(foodDetail, 1, foodOrder);
	}


	
	
	@Test(expected = FoodNotFoundException.class)
	public void convertToFoodOrderItemNegative() throws EmployeeNotFoundException, FoodNotFoundException, VendorNotFoundException {
	foodDetail.setFoodId(11);
	Mockito.when(vendorRepository.findById(1)).thenReturn(Optional.of(vendor));
	Mockito.when(foodRepository.findById(6)).thenReturn(Optional.of(food));
	employeeServiceImpl.convertToFoodOrderItem(foodDetail, 1, foodOrder);
	}
	
	@Test(expected = FoodNotFoundException.class)
	public void convertToFoodOrderItemVendorFoodNegative() throws EmployeeNotFoundException, FoodNotFoundException, VendorNotFoundException {
	foodDetail.setFoodId(6);
	Mockito.when(vendorRepository.findById(1)).thenReturn(Optional.of(vendor));
	Mockito.when(foodRepository.findById(6)).thenReturn(Optional.of(food));
	Food food1= new Food();
	Mockito.when(vendorFoodRepository.findByVendorIdAndFoodId(vendor,food1)).thenReturn(Optional.of(vendorFood));
	employeeServiceImpl.convertToFoodOrderItem(foodDetail, 1, foodOrder);
	}

	


	
	


}
