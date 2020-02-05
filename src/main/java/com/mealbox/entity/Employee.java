package com.mealbox.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mealbox.common.MealboxEnum.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long employeeId;
	private String employeeName;
	private String password;
	private String email;
	private Long mobile; 
	@Enumerated(EnumType.STRING)
	private Role role;
}
