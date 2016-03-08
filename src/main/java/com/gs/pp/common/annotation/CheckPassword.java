package com.gs.pp.common.annotation;

import com.gs.pp.validator.CheckPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类级别验证器
 * 使用时,直接放在类头上即可 见 {User}
 * Created by duronghong on 2016/3/3.
 * Email: drh0534@163.com
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//指定验证器
@Constraint(validatedBy = CheckPasswordValidator.class)
public @interface CheckPassword {

    //默认错误消息
    String message() default "";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CheckPassword[] value();
    }
}
