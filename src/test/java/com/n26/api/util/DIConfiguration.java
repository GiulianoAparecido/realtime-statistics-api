package com.n26.api.util;

import com.n26.api.statistic.cache.StatisticCache;
import com.n26.api.statistic.event.Publisher;
import com.n26.api.statistic.event.StatisticPublisher;
import com.n26.api.transaction.validation.Validation;
import com.n26.api.transaction.validation.Validator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;

@Profile("test")
@Configuration
public class DIConfiguration {

    @Bean
    @Primary
    public Publisher statisticPublisher() {
        return Mockito.mock(StatisticPublisher.class);
    }

    @Bean
    @Primary
    public Validation validator() {
        return Mockito.mock(Validator.class);
    }

    @Bean
    @Primary
    public StatisticCache statisticCache() {
        return Mockito.mock(StatisticCache.class);
    }
}