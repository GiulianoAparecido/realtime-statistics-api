package com.n26.api.statistic.data;

import com.n26.api.config.BigDecimalConfig;

import java.math.BigDecimal;

public class StatisticData {
    private String sum = "0.00";
    private String avg = "0.00";
    private String max = "0.00";
    private String min = "0.00";
    private Long count = 0L;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static class StatisticDataBuilder{
        private StatisticData statisticData;

        public StatisticDataBuilder(){
            statisticData = new StatisticData();
        }

        public StatisticDataBuilder sum(BigDecimal sum){
            statisticData.setSum(formatBigDecimal(sum).toString());
            return this;
        }

        public StatisticDataBuilder avg(BigDecimal avg){
            statisticData.setAvg(formatBigDecimal(avg).toString());
            return this;
        }

        public StatisticDataBuilder max(BigDecimal max){
            statisticData.setMax(formatBigDecimal(max).toString());
            return this;
        }

        public StatisticDataBuilder min(BigDecimal min){
            statisticData.setMin(formatBigDecimal(min).toString());
            return this;
        }

        public StatisticDataBuilder count(Long count){
            statisticData.setCount(count);
            return this;
        }

        public StatisticData build(){
            return statisticData;
        }

        private BigDecimal formatBigDecimal(BigDecimal unformatted){
            return unformatted.setScale(BigDecimalConfig.DECIMAL_PLACES, BigDecimalConfig.ROUNDING_MODE);
        }
    }
}
