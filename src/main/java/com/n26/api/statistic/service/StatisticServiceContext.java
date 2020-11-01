package com.n26.api.statistic.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class StatisticServiceContext {
    private BigDecimal amount;
    private Instant timestamp;
    private Instant instantNow;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getInstantNow() {
        return instantNow;
    }

    public void setInstantNow(Instant instantNow) {
        this.instantNow = instantNow;
    }

    public static class StatisticServiceContextBuilder {
        private StatisticServiceContext context;

        public StatisticServiceContextBuilder(){
            context = new StatisticServiceContext();
        }

        public StatisticServiceContextBuilder amount(String amount){
            context.setAmount(new BigDecimal(amount));
            return this;
        }

        public StatisticServiceContextBuilder timestamp(String timestamp){
            context.setTimestamp(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(timestamp)));
            return this;
        }

        public StatisticServiceContextBuilder instantNow(Instant instantNow){
            context.setInstantNow(instantNow);
            return this;
        }

        public StatisticServiceContext build(){
            return context;
        }
    }
}
