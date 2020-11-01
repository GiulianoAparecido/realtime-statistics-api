package com.n26.api.statistic.model;

import com.n26.api.config.BigDecimalConfig;
import com.n26.api.statistic.data.StatisticData;

import java.math.BigDecimal;
import java.time.Instant;

public class Statistic {
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal avg = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.ZERO;
    private Long count = 0L;

    private Instant lastEntryInstant;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Instant getLastEntryInstant(){return this.lastEntryInstant;}

    public void setLastEntryInstant(Instant instant){
        this.lastEntryInstant = instant;
    }

    public boolean isStatisticBucketValid(Instant instant){
        return this.lastEntryInstant != null && this.lastEntryInstant.isAfter(instant);
    }

    public void update(BigDecimal newAmount){ //TODO: temporal dependency
        incrementCount();
        calculateSum(newAmount);
        calculateAvg();
        calculateMax(newAmount);
        calculateMin(newAmount);
    }

    public void combine(Statistic other) {
        count += other.count;
        calculateSum(other.sum);
        calculateMin(other.min);
        calculateMax(other.max);
        calculateAvg();
    }

    public void reset(){
        this.sum = BigDecimal.ZERO;
        this.avg = BigDecimal.ZERO;
        this.max = BigDecimal.ZERO;
        this.min = BigDecimal.ZERO;
        this.count = 0L;
    }

    private void calculateSum(BigDecimal newAmount){
        sum = sum.add(newAmount);
    }

    private void incrementCount(){
        count++;
    }

    private void calculateAvg(){
        avg = sum.divide(BigDecimal.valueOf(count), BigDecimalConfig.DECIMAL_PLACES, BigDecimalConfig.ROUNDING_MODE);
    }

    private void calculateMin(BigDecimal newAmount) {
        if(BigDecimal.ZERO.compareTo(min) == 0){
            min = newAmount;
            return;
        }
        min = min.min(newAmount);
    }

    private void calculateMax(BigDecimal newAmount) {
        if(BigDecimal.ZERO.compareTo(max) == 0){
            max = newAmount;
            return;
        }
        max = max.max(newAmount);
    }

    public StatisticData toData(){
        return new StatisticData.StatisticDataBuilder()
                .sum(sum)
                .avg(avg)
                .max(max)
                .min(min)
                .count(count)
                .build();
    }

    public static class StatisticBuilder{
        private Statistic statistic;

        public StatisticBuilder(){
            statistic = new Statistic();
        }

        public StatisticBuilder fromAmount(BigDecimal amount){
            statistic.update(amount);
            return this;
        }

        public StatisticBuilder sum(BigDecimal sum){
            statistic.setSum(sum);
            return this;
        }

        public StatisticBuilder max(BigDecimal max){
            statistic.setMax(max);
            return this;
        }

        public StatisticBuilder min(BigDecimal min){
            statistic.setMin(min);
            return this;
        }

        public StatisticBuilder avg(BigDecimal avg){
            statistic.setAvg(avg);
            return this;
        }

        public StatisticBuilder count(Long count){
            statistic.setCount(count);
            return this;
        }

        public StatisticBuilder lastEntryInstant(Instant instant){
            statistic.setLastEntryInstant(instant);
            return this;
        }

        public Statistic build(){
            return statistic;
        }
    }
}
