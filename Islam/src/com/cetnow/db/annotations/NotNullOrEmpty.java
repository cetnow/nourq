package com.cetnow.db.annotations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cetnow.db.validator.NotNullOrEmptyValidator;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullOrEmptyValidator.class)
@Documented
public @interface NotNullOrEmpty {

	String message() default "StringNullOrempty";
	Class<? extends Payload>[] payload() default {};
	Class<?>[] groups() default {};
}
