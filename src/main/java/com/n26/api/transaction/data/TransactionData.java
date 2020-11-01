package com.n26.api.transaction.data;

public class TransactionData {
    private String amount;
    private String timestamp;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class TransactionDataBuilder{
        private TransactionData transactionData;

        public TransactionDataBuilder(){
            transactionData = new TransactionData();
        }

        public TransactionDataBuilder amount(String amount){
            transactionData.setAmount(amount);
            return this;
        }

        public TransactionDataBuilder timestamp(String timestamp){
            transactionData.setTimestamp(timestamp);
            return this;
        }

        public TransactionData build(){
            return transactionData;
        }
    }
}
