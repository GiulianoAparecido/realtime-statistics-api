package com.n26.api.statistic.controller;

import com.n26.api.statistic.data.StatisticData;
import com.n26.api.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class StatisticController{

    @Autowired
    private StatisticService last60SecondsStatisticService;

    @GetMapping(value = "/statistics", produces = "application/json")
    public StatisticData getStatistics() {
        return last60SecondsStatisticService.getStatistics(Instant.now()).toData();
    }
}
