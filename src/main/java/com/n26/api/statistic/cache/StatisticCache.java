package com.n26.api.statistic.cache;

import com.n26.api.statistic.model.Statistic;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Notes from Requirements:
 *
 * It was not clear if milliseconds from transaction's timestamp should be considered.
 * In this implementation it is considered.
 */
@Component
public class StatisticCache {

    private ConcurrentHashMap<Integer, Statistic> statisticBuckets = new ConcurrentHashMap<>();

    public Statistic getOrElseCreateNew(Integer key){
        return statisticBuckets.computeIfAbsent(key, k -> new Statistic()); //atomically
    }

    public List<Statistic> getAllFrom(Instant instantFrom){
        return statisticBuckets.values().parallelStream()
                .filter(s -> s.isStatisticBucketValid(instantFrom))
                .collect(Collectors.toList());
    }

    public synchronized void clear(){
        statisticBuckets.clear();
    }
}
