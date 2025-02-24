package com.sdk.store.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {
	//@RequestMapping(value = "/store/{path:[^\\.]*}/**")
	//@RequestMapping(value = "app/store/{path:[^\\.]*}/**")
	//@RequestMapping(value = "/app/store/**")
    //public String forward() {
    //    return "forward:/store";
    //}
	
    @RequestMapping(value = {"/store/**"})
    public String forward() {
        return "/web/index.html";
    }
}
