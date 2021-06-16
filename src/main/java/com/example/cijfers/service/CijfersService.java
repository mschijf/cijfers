package com.example.cijfers.service;

import com.example.cijfers.model.CijfersData;
import com.example.cijfers.model.SolutionMetadata;
import com.example.cijfers.service.calculation.Expression;
import com.example.cijfers.service.calculation.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CijfersService {

    private static final Logger log = LoggerFactory.getLogger(CijfersService.class);

    public Optional<CijfersData> doCalculate(ArrayList<Integer> input, int toBeReached) {
        Instant start = Instant.now();
        Expression.operationCount = 0;
        Expression result =  Game.findNearestSolution(input, toBeReached);
        double ms = (Duration.between(start, Instant.now()).toMillis()/1000.0);
        SolutionMetadata metaData = new SolutionMetadata(ms, Expression.operationCount);
        CijfersData data = new CijfersData(input, toBeReached, result.toString(),metaData);
        return Optional.of(data);
    }
}

