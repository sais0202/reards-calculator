package com.customer.rewards.services;

import com.customer.rewards.domain.CustomerTransaction;

import java.util.List;

public interface CustomerRewardsService {

    Long calculate(List<CustomerTransaction> customerTransactions);
}
