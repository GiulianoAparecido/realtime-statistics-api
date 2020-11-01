package com.n26.api.transaction.validation;

import com.n26.api.transaction.data.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validator implements Validation{

    @Autowired
    private List<Validation> validations;

    @Override
    public void validate(TransactionData transactionData) throws ValidationException {
        for (Validation v : validations) {
            v.validate(transactionData);
        }
    }
}
