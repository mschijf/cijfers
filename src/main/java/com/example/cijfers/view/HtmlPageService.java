package com.example.cijfers.view;

import com.example.cijfers.model.CijfersData;
import com.example.cijfers.service.CijfersService;
import com.example.cijfers.service.inputRules.RandomCijfersArrayList;
import com.example.cijfers.service.inputRules.RandomToBeReached;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class HtmlPageService {

    private static final Logger log = LoggerFactory.getLogger(HtmlPageService.class);
    private final CijfersService cijfersService;

    @Autowired
    public HtmlPageService(CijfersService cijfersService) {
        this.cijfersService = cijfersService;
    }

    public String getPage() {

        TemplateLoader loader = new ClassPathTemplateLoader("/handlebars", ".hbs");
        Handlebars handlebars = new Handlebars(loader);
        try {
            Template template = handlebars.compile("cijfers-page");

            ArrayList<Integer> input = new RandomCijfersArrayList(6);
            int toBeReached = RandomToBeReached.get();
            Optional<CijfersData> data = cijfersService.doCalculate(input, toBeReached);

            return template.apply(data.get());
        } catch (IOException ioe) {
            log.error("Error during creating cijfers page", ioe);
            return "Error during creating cijfers page";
        }
    }
}
