package com.n26.api.statistic.event;

public interface Publisher {

    void publish(Event event);
}
