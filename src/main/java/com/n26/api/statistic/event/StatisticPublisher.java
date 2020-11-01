package com.n26.api.statistic.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class StatisticPublisher implements Publisher {

    private final ApplicationEventPublisher publisher;

    public StatisticPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Event event) {
        publisher.publishEvent(event);
    }
}
