package com.gs.pp.web;

import com.gs.pp.validator.UserValidator;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class BaseController {

    UserValidator userValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {




		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
		binder.registerCustomEditor(int.class, new PropertyEditorSupport(){

			@Override
			public String getAsText() {
				// TODO Auto-generated method stub
				return getValue().toString();
			}

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				// TODO Auto-generated method stub
				setValue(Integer.parseInt(text));
			}
			
		});
	}

}
