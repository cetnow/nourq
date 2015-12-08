package com.cetnow.db.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cetnow.db.DAL;
import com.cetnow.db.annotations.LookupAnnotation;

public class LookupValidator implements ConstraintValidator<LookupAnnotation, Object>{

	@Autowired
	private DAL dal; 
	
	int lookupCat;
	@Override
	public void initialize(LookupAnnotation constraintAnnotation) {
		lookupCat=constraintAnnotation.value();
		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		int val=0;
		try{
			val=Integer.parseInt(value+"");
		}catch(Exception ex){}
		if(val==0)
			return false;
		return dal.isValidLookup(val, lookupCat);
	}
    
}
