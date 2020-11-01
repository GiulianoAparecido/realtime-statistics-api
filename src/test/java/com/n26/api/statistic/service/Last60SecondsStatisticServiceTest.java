package com.n26.api.statistic.service;

import com.n26.Application;
import com.n26.api.config.BigDecimalConfig;
import com.n26.api.statistic.cache.StatisticCache;
import com.n26.api.statistic.model.Statistic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Last60SecondsStatisticServiceTest {

    @Autowired
    private StatisticCache statisticCache;

    @Autowired
    private StatisticService last60SecondsStatisticService;

    @Test
    public void getStatistics(){
        Instant anyInstant = Instant.now();
        Statistic firstStatistic = new Statistic.StatisticBuilder().fromAmount(BigDecimal.ONE).build();
        Statistic secondStatistic = new Statistic.StatisticBuilder().fromAmount(BigDecimal.TEN).build();
        Statistic expectedStatistic = new Statistic.StatisticBuilder()
                .count(2L)
                .sum(BigDecimal.valueOf(11))
                .max(BigDecimal.TEN)
                .min(BigDecimal.ONE)
                .avg(BigDecimal.valueOf(5.5).setScale(BigDecimalConfig.DECIMAL_PLACES,BigDecimalConfig.ROUNDING_MODE))
                .build();
        Mockito.when(statisticCache.getAllFrom(ArgumentMatchers.any(Instant.class)))
                .thenReturn(Arrays.asList(firstStatistic, secondStatistic));

        Statistic finalStatistic = last60SecondsStatisticService.getStatistics(anyInstant);

        assertEquals(expectedStatistic.getCount(), finalStatistic.getCount());
        assertEquals(expectedStatistic.getSum(), finalStatistic.getSum());
        assertEquals(expectedStatistic.getAvg(), finalStatistic.getAvg());
        assertEquals(expectedStatistic.getMax(), finalStatistic.getMax());
        assertEquals(expectedStatistic.getMin(), finalStatistic.getMin());
    }
}
