package com.n26.api.statistic.service;

import com.n26.api.config.TransactionConfig;
import com.n26.api.statistic.model.Statistic;
import com.n26.api.statistic.cache.StatisticCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

@Service
public class Last60SecondsStatisticService implements StatisticService {

    @Autowired
    private StatisticCache statisticCache;

    @Override
    public Statistic getStatistics(Instant instantNow){
        Instant instant60SecondsAgo = instantNow.minusSeconds(TransactionConfig.VALID_PERIOD_SECONDS);
        return statisticCache.getAllFrom(instant60SecondsAgo).parallelStream()
                .collect(Statistic::new, Statistic::combine, Statistic::combine);
    }

    @Override
    public synchronized void updateStatistic(StatisticServiceContext context){
        Statistic statistic = getStatisticOnInstant(context.getTimestamp());

        Instant instant60SecondsAgo = context.getInstantNow().minusSeconds(TransactionConfig.VALID_PERIOD_SECONDS);
        if(statistic.isStatisticBucketValid(instant60SecondsAgo)){
            statistic.update(context.getAmount());
        }else{
            statistic.reset();
            statistic.update(context.getAmount());
            statistic.setLastEntryInstant(context.getTimestamp());
        }
    }

    @Override
    public void deleteStatistic() {
        statisticCache.clear();
    }

    private Statistic getStatisticOnInstant(Instant instant){
        int statisticKey = buildKey(instant);
        return statisticCache.getOrElseCreateNew(statisticKey);
    }

    private int buildKey(Instant instant) {
        int secondsFromTimestamp = instant.get(ChronoField.MILLI_OF_SECOND);
        int millisecondsFromTimestamp = instant.atZone(ZoneOffset.UTC).getSecond();
        return secondsFromTimestamp * 1000 + millisecondsFromTimestamp;
    }
}
