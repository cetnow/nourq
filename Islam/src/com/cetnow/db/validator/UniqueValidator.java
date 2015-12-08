package com.cetnow.db.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cetnow.db.DAL;
import com.cetnow.db.annotations.*;
import com.cetnow.util.Html;

public class UniqueValidator implements ConstraintValidator<Unique, String>{

	@Autowired
	private DAL dal; 
	
	@Autowired
	private Html html;
	
	String table;
	String column;
	
	@Override
	public void initialize(Unique constraintAnnotation) {
		table=constraintAnnotation.table();
		column=constraintAnnotation.column();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(html.isNullOrEmpty(value))
			return true;
		return dal.isUnique(table, column, value);
	}
    
}
