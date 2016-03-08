package com.gs.pp.validator;

import com.gs.pp.orm.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors e) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmpty(e, "name", "name is empty");
		User user = (User) target;
		if (user.getAge() < 0) {
			e.rejectValue("age", "negative");
		} else if (user.getAge() > 110) {
			e.rejectValue("age", "too.darn.old");
		}

	}

}
