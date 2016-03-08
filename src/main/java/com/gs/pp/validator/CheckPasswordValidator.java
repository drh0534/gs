package com.gs.pp.validator;

import com.gs.pp.common.annotation.CheckPassword;
import com.gs.pp.orm.User;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by duronghong on 2016/3/3.
 * Email: drh0534@163.com
 */
public class CheckPasswordValidator implements ConstraintValidator<CheckPassword,User> {
    @Override
    public void initialize(CheckPassword checkPassword) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if(user==null){
            return false;
        }
        if(!StringUtils.hasText(user.getPassword())){
            //禁用默认的约束
            context.disableDefaultConstraintViolation();
            //定义我们自己的约束
            context.buildConstraintViolationWithTemplate("{user.password.null}")
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
