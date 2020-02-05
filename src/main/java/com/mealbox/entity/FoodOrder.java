package com.mealbox.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import com.mealbox.common.MealboxEnum.OrderStatus;
import com.mealbox.common.MealboxEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SequenceGenerator(name = "foodOrderId", initialValue = 101, allocationSize = 1)
@Entity
public class FoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foodOrderId")
	private Long foodOrderId;	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employeeId; 
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@CreationTimestamp
	private LocalDate orderDate;
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType; 
	private Double totalAmount;
}
