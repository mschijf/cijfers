package com.example.cijfers.controller;

import com.example.cijfers.model.CijfersData;
import com.example.cijfers.service.CijfersService;
import com.example.cijfers.service.inputRules.RandomCijfersArrayList;
import com.example.cijfers.service.inputRules.RandomToBeReached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;


@RequestMapping(value = "/v1")
@RestController
public class CijfersController {

    private final CijfersService cijfersService;

    @Autowired
    public CijfersController(CijfersService cijfersService) {
        this.cijfersService = cijfersService;
    }

    @GetMapping("/cijfers/")
    public ResponseEntity<CijfersData> getCijfersData() {
        ArrayList<Integer> input = new RandomCijfersArrayList(6);
        int toBeReached = RandomToBeReached.get();
        
        Optional<CijfersData> data = cijfersService.doCalculate(input, toBeReached);
        return data.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}