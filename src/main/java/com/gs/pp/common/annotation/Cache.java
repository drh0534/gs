package com.gs.pp.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gs.pp.common.utils.RedisUtils;



/**
 * 
 * @作者 zhuhh
 * @描述   自定义业务层的缓存注解   
 * @创建时间 2015年10月25日 下午5:47:24
 * @修改时间
 */

@Target({ElementType.METHOD})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented
public @interface Cache {
   
	/**
	 * 缓存key
	 * @return
	 */
	CacheKey key() default CacheKey.LIST_ALBUM_ALL_VISITED;
	
	
	/**
	 * 缓存时间
	 * @return
	 */
	int time() default RedisUtils.TIME_3600;
	
}
