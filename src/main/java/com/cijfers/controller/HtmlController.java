package com.cijfers.controller;

import com.cijfers.view.HtmlPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/")
@RestController
public class HtmlController {

    private final HtmlPageService htmlPageService;

    @Autowired
    public HtmlController(HtmlPageService htmlPageService) {
        this.htmlPageService = htmlPageService;
    }

    @GetMapping("/html")
    public String getCijfersPage() {
        return htmlPageService.getPage();
    }

}