package com.gs.pp.web;

import com.gs.pp.orm.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/one",method=RequestMethod.GET)
    @ResponseBody
	public User test(){
		User user = new User();
		user.setEmail("aa@163.com");
		user.setId(1);
		user.setName("aa");
		user.setPassword("bb");
		return user;
	}

}
