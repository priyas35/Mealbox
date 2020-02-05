package com.mealbox.common;

/**
 * MealboxEnum is the enum for the maintaining the values of the Role type of
 * the employee, admin.
 * 
 * @author Govindasamy.C
 * @since 05-02-2020
 *
 */
public class MealboxEnum {

	/**
	 * Maintaining the role type such as admin, vendor and employee
	 * 
	 * @author Govindasamy
	 * @since 05-02-2020
	 */
	public enum Role {
		ADMIN, VENDOR, EMPLOYEE;

	}
	
	/**
	 * Maintaining the food type such as veg and non-veg
	 * 
	 * @author Govindasamy
	 * @since 05-02-2020
	 */
	public enum FoodType {
		VEG, NON_VEG;

	}
	
	/**
	 * Maintaining the food status type such as available and unavailable
	 * 
	 * @author Govindasamy
	 * @since 05-02-2020
	 */
	public enum FoodStatus {
		AVAILABLE, UNAVAILABLE;

	}
	
	/**
	 * Maintaining the food order status type such as Ordered, Pending, Cancelled
	 * 
	 * @author Govindasamy
	 * @since 05-02-2020
	 */
	public enum OrderStatus {
		ORDERED, PENDING, CANCELLED;

	}
	
	/**
	 * Maintaining the payment types such as PayTm and PhonePe types.
	 * 
	 * @author Govindasamy
	 * @since 05-02-2020
	 */
	public enum PaymentType {
		
		PAYTM, PHONEPE;
	}
}
