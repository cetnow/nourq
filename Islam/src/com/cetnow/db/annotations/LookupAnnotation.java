package com.cetnow.db.annotations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cetnow.db.validator.LookupValidator;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = LookupValidator.class)
@Documented
public @interface LookupAnnotation {

	String message() default "LookupAnnotationsMessage";
	int value();
	Class<? extends Payload>[] payload() default {};
	Class<?>[] groups() default {};
}
