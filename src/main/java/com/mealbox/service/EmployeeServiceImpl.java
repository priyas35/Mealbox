package com.mealbox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealbox.common.MealboxEnum;
import com.mealbox.constant.Constant;
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

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author Chethana
 * @since 05-February-2020
 * @version 1.0
 *
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	VendorFoodRepository vendorFoodRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	PaymentRegistry paymentRegistry;
	
	@Autowired
	FoodOrderItemRepository foodOrderItemRepository;

	@Autowired
	FoodOrderRepository foodOrderRepository;

	
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
			log.error("Entering into EmployeeServiceImpl authenticateEmployee method:" + Constant.EMPLOYEE_NOT_FOUND);
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
	 * This method is used to place a new order from the existing authorized vendor stall with their available food menu
	 * 
	 * @author Chethana
	 * @param orderRequestDto - Takes parameters like Food Details,Vendor Id,Payment Opted
	 * @param employeeId - takes of type Long which is the Employee SAP Id
	 * @return OrderResponseDto - returns places order Id along with the status codes
	 * @throws EmployeeNotFoundException -  thrown when the logged in employee details is invalid
	 * @throws FoodNotFoundException - thrown when the Food ordered doesn't belong to the existing menu
	 * @since 05-February-2020
	 */
	@Transactional
	public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto,Long employeeId) throws EmployeeNotFoundException, FoodNotFoundException {
		log.info("Entering into placeOrder() method of EmployeeServiceImpl");
		Optional<Employee> employeeResponse=employeeRepository.findByEmployeeId(employeeId);
		if(!employeeResponse.isPresent()) {
			log.error("Exception occured in placeOrder() method of EmployeeServiceImpl:"+Constant.EMPLOYEE_NOT_FOUND);
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		}
		
		FoodOrder foodOrder= new FoodOrder();
		BeanUtils.copyProperties(orderRequestDto, foodOrder);	
		foodOrder.setOrderStatus(MealboxEnum.OrderStatus.ORDERED);
		foodOrder.setEmployeeId(employeeResponse.get());
		foodOrderRepository.save(foodOrder);
		
		//Service locator Factory Bean Changes to perform payment
		String message=paymentRegistry.getServiceBean(orderRequestDto.getPaymentType().toString()).payment();
		
		//converting the food details and saving it into foodOrderItem table to track the individual food ordered details 
		
		List<FoodDetail> foodDetailList=orderRequestDto.getFoodList();
		List<FoodOrderItem> foodOrderList=foodDetailList.stream().map(index->{
			try {
				return convertToFoodOrderItem(index,orderRequestDto.getVendorId(),foodOrder);
			} catch (EmployeeNotFoundException | FoodNotFoundException |VendorNotFoundException  e) {
				log.error("Exception occured in placeOrder() method of EmployeeServiceImpl:"+e.getMessage());
			}
			return null;
			
		}).collect(Collectors.toList());	
		
		if(Objects.isNull(foodOrderList)) {
			log.error("Exception occured in placeOrder() method of EmployeeServiceImpl:foodOrderList is empty");
			throw new EmployeeNotFoundException("Exception occured in placeOrder() method of EmployeeServiceImpl:foodOrderList is empty");
		}
		
		
		foodOrderItemRepository.saveAll(foodOrderList);
		
		OrderResponseDto orderResponseDto=new OrderResponseDto();
		orderResponseDto.setMessage(message);
		orderResponseDto.setFoodOrderId(foodOrder.getFoodOrderId());
		return orderResponseDto;
	}
	
	/**
	 * This method converts the list obtained from UI to the FoodOrderItem Object mapping by entities
	 * 
	 * @author Chethana
	 * @param foodDetail - contains foodId,quantity
	 * @param vendorId - cafe stall Id
	 * @param foodOrder - FoodOrder table object->contains order details
	 * @return FoodOrderItem - converted persistent object type
	 * @throws EmployeeNotFoundException - when the logged on employee is invalid
	 * @throws FoodNotFoundException - when the ordered food is invalid
	 * @throws VendorNotFoundException - when the vendor is invalid
	 * 
	 */
	FoodOrderItem convertToFoodOrderItem(FoodDetail foodDetail,Integer vendorId,FoodOrder foodOrder) throws EmployeeNotFoundException, FoodNotFoundException, VendorNotFoundException {	
		Optional<Vendor> vendor=vendorRepository.findById(vendorId);
		if(!vendor.isPresent()) {
			log.error("Exception occured in convertToFoodOrder():"+Constant.VENDOR_NOT_FOUND);
			throw new VendorNotFoundException(Constant.VENDOR_NOT_FOUND);
		}
		
		Optional<Food> food=foodRepository.findById(foodDetail.getFoodId());
		if(!food.isPresent()) {
			log.error("Exception occured in convertToFoodOrder() method of EmployeeServiceImpl:"+Constant.FOOD_NOT_FOUND);
			throw new FoodNotFoundException(Constant.FOOD_NOT_FOUND);
		}
		
		Optional<VendorFood> vendorFood=vendorFoodRepository.findByVendorIdAndFoodId(vendor.get(),food.get());
		if(!vendorFood.isPresent()) {
			log.error("Exception occured in convertToFoodOrder() method of EmployeeServiceImpl:"+Constant.FOOD_UNAVAILABLE);
			throw new FoodNotFoundException(Constant.FOOD_UNAVAILABLE);
		}
		
		FoodOrderItem foodOrderItem= new FoodOrderItem();
		foodOrderItem.setVendorFoodId(vendorFood.get());
		foodOrderItem.setFoodOrderId(foodOrder);
		foodOrderItem.setQuantity(foodDetail.getQuantity());
		
		return foodOrderItem;
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
			log.error("Entering into EmployeeServiceImpl getMyOrder method:" + Constant.EMPLOYEE_NOT_FOUND);
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		}
		List<FoodOrder> foodOrder = foodOrderRepository.findByEmployeeId(employee.get());
		if (foodOrder.isEmpty()) {
			log.error("Entering into EmployeeServiceImpl getMyOrder method:" + Constant.ORDER_NOT_FOUND);
			throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
		}
		List<OrderDetailsResponseDto> myOrders = new ArrayList<>();
		log.info("Entering into EmployeeServiceImpl getMyOrder method:Getting my order details");
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
