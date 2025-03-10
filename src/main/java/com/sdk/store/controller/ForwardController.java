package com.sdk.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {
	//@RequestMapping(value = "/store/{path:[^\\.]*}/**")
	//@RequestMapping(value = "app/store/{path:[^\\.]*}/**")
	//@RequestMapping(value = "/app/store/**")
    //public String forward() {
    //    return "forward:/store";
    //}
	/* 
    @GetMapping("/")
    public String index() {
        return "index";
    }
    */
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }
    
    @GetMapping(value = "/store/**")
    public String forwardToStore() {
        return "index";
    }
}
