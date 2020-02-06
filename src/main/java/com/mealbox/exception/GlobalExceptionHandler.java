package com.mealbox.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mealbox.constant.Constant;
import com.mealbox.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle the not valid field errors along with validation messages and get the
	 * list of multiple field errors.
	 * 
	 * @author Govindasamy.C
	 * @since 05-02-2020
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("date", LocalDateTime.now());
		body.put("status", status.value());

		// Get all errors for field valid
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());

		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, HttpStatus.OK);
	}

	@ExceptionHandler(VendorNotFoundException.class)
	public ResponseEntity<ErrorDto> vendorNotFoundException(VendorNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorStatusMessage(Constant.VENDOR_NOT_FOUND);
		errorDto.setErrorStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorDto> orderNotFoundException(OrderNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorStatusMessage(Constant.ORDER_NOT_FOUND);
		errorDto.setErrorStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorDto> employeeNotFoundException(EmployeeNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorStatusMessage(Constant.EMPLOYEE_NOT_FOUND);
		errorDto.setErrorStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	
	@ExceptionHandler(FoodNotFoundException.class)
	public ResponseEntity<ErrorDto> foodNotFoundException(){
		ErrorDto errorDto= new ErrorDto();
		errorDto.setErrorStatusMessage(Constant.FOOD_NOT_FOUND);
		errorDto.setErrorStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
	
	
}
