package com.n26.api.transaction.validation;

import com.n26.api.transaction.data.TransactionData;

public interface Validation {
    void validate(TransactionData transactionData);
}
