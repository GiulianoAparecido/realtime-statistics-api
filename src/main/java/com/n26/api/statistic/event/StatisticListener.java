package com.n26.api.statistic.event;

import com.n26.api.statistic.service.StatisticService;
import com.n26.api.statistic.service.StatisticServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StatisticListener {

    @Autowired
    private StatisticService last60SecondsStatisticService;

    @EventListener
    public void handle(StatisticEvent event) {
        updateStatistics(event);
    }

    @EventListener
    public void handle(DeleteEvent event) {
        last60SecondsStatisticService.deleteStatistic();
    }

    private void updateStatistics(StatisticEvent event) {
        StatisticServiceContext context = new StatisticServiceContext.StatisticServiceContextBuilder()
                .amount(event.getAmount())
                .timestamp(event.getTransactionTimestamp())
                .instantNow(event.getInstantNow())
                .build();
        last60SecondsStatisticService.updateStatistic(context);
    }
}
