package com.gs.pp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by duronghong on 2016/3/1.
 * Email: drh0534@163.com
 */
@Controller
@RequestMapping("/gs")
public class IndexController {

    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }
}
