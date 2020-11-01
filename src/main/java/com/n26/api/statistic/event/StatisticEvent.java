package com.n26.api.statistic.event;

import java.time.Instant;

public class StatisticEvent implements Event {
    private String amount;
    private String transactionTimestamp;
    private Instant instantNow;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(String transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public Instant getInstantNow() {
        return instantNow;
    }

    public void setInstantNow(Instant instantNow) {
        this.instantNow = instantNow;
    }

    public static class StatisticEventBuilder {
        private StatisticEvent event;

        public StatisticEventBuilder(){
            event = new StatisticEvent();
        }

        public StatisticEventBuilder amount(String amount){
            event.setAmount(amount);
            return this;
        }

        public StatisticEventBuilder transactionTimestamp(String timestamp){
            event.setTransactionTimestamp(timestamp);
            return this;
        }

        public StatisticEventBuilder instantNow(Instant instantNow){
            event.setInstantNow(instantNow);
            return this;
        }

        public StatisticEvent build(){
            return event;
        }
    }
}
