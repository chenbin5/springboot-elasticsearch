package com.gome.controller;

import com.gome.service.HtmlContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 13:47
 */
@RestController
public class HtmlContentController {


    @Autowired
    private HtmlContentService htmlContentService;

    @GetMapping("/parse/{keywords}")
    public Boolean parsingHtml(@PathVariable("keywords") String keywords) {
        Boolean flag = htmlContentService.parsingContent(keywords);
        return flag;
    }

    @GetMapping("/log/add")
    public String getLog(@RequestParam(value = "keywords", required = false) String keywords,
                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "activityState", required = false) Integer activityState,
                         @RequestParam(value = "goodsName", required = false) String goodsName,
                         @RequestParam(value = "type", required = false) Integer type) {
        System.out.println(keywords);
        return "getLog";
    }
}
