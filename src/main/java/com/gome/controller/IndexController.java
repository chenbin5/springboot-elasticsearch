package com.gome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 11:21
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String toIndexPage() {
        return "index";
    }
}
