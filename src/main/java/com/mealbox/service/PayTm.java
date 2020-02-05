package com.mealbox.service;

import org.springframework.stereotype.Service;

import com.mealbox.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Service("PAYTM")
@Slf4j
public class PayTm {
	
	public class PhonePe implements PaymentService{

		@Override
		public String payment() {
			log.info("Executing PayTm:Payment succedded using PAYTM");
			return Constant.PAYTM_MSG;
		}

	}
}
