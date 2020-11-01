package com.n26.api.statistic.service;

import com.n26.api.statistic.model.Statistic;

import java.time.Instant;

public interface StatisticService {

    Statistic getStatistics(Instant now);
    void updateStatistic(StatisticServiceContext context);
    void deleteStatistic();
}
