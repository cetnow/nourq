package com.cetnow.db.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cetnow.db.annotations.LookupAnnotation;
import com.cetnow.db.annotations.NotNullOrEmpty;
import com.cetnow.util.Html;

public class NotNullOrEmptyValidator implements ConstraintValidator<NotNullOrEmpty, String>{

	@Autowired
	Html html;

	@Override
	public void initialize(NotNullOrEmpty constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !html.isNullOrEmpty(value);
	}
    
}
