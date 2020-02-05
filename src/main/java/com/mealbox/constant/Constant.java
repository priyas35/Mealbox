package com.mealbox.constant;

public class Constant {
	
	private Constant() {

	}

	//Common 
	public static final String FAILURE_MESSAGE = "FAILURE";
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	public static final String NO_RECORDS_FOUND = "No Records Found";
	public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
	public static final String AUTHENTICATION_SUCCESSFUL = "Authentication Successful";
	public static final String AUTHENTICATION_FAILED = "Authentication Failed";
	public static final Integer AUTHENTICATION_SUCCESSFUL_CODE = 200;
	public static final String FOOD_NOT_FOUND = "Food Unavailable";
	
	public static final String PHONEPE_MSG = "Order Placed successfully..Payment succedded using Phonepe";
	public static final String PAYTM_MSG = "Order Placed successfully..Payment succedded using PayTm";

	public static final String ORDER_NOT_FOUND = "Order not found";
	public static final String ORDERED_FOOD_NOT_FOUND = "There is no foods";
	
	//Vendor
	public static final String VENDOR_ID_NOT_NULL_MESSAGE = "Vendor ID should be not null";
	public static final String VENDOR_NAME_NOT_NULL_MESSAGE = "Vendor Name should be not null";
	public static final String VENDOR_NOT_FOUND = "Vendor not found";
	public static final String VENDOR_ADDED_SUCCESSFULLY = "Vendor Added Successfully";
	public static final String VENDOR_FOOD_ADDED_SUCCESSFULLY = "Vendor Food Added Successfully";
	
	//Vendor Food
	public static final String VENDOR_FOOD_NAME_NOT_NULL_MESSAGE = "Vendor food name should be not null";
	public static final String VENDOR_FOOD_TYPE_NOT_NULL_MESSAGE = "Vendor food type should be not null";
	
	public static final String FOOD_UNAVAILABLE = "Food Item is not available with this current vendor";
	
}
