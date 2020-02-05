package com.mealbox.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentRegistry {

	public PaymentService getServiceBean(String paymentType);
}
