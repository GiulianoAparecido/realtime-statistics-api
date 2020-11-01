package com.n26.api.statistic.cache;

import com.n26.api.statistic.model.Statistic;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class StatisticCacheTest {

    @Test
    public void createStatisticToCacheWhenNew(){
        StatisticCache sut = new StatisticCache();
        Integer anyKey = 123;

        Statistic newStatistic = sut.getOrElseCreateNew(anyKey);

        assertTrue(BigDecimal.ZERO.compareTo(newStatistic.getSum()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(newStatistic.getAvg()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(newStatistic.getMax()) == 0);
        assertTrue(BigDecimal.ZERO.compareTo(newStatistic.getMin()) == 0);
        assertTrue(newStatistic.getCount() == 0L);
    }

    @Test
    public void getStatisticFromCacheWhenExisting(){
        StatisticCache sut = new StatisticCache();
        Integer existingKey = 123;
        BigDecimal existingAmount = BigDecimal.ONE;
        addStatisticToCache(sut, existingKey, BigDecimal.ONE);

        Statistic existingStatistic = sut.getOrElseCreateNew(existingKey);

        assertEquals(existingAmount, existingStatistic.getSum());
    }

    private void addStatisticToCache(StatisticCache cache, Integer key, BigDecimal amount) {
        Statistic existingStatistic = cache.getOrElseCreateNew(key);
        existingStatistic.setSum(amount);
    }
}
