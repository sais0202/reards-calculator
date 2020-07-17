package com.customer.rewards.services.impl;

import com.customer.rewards.domain.CustomerTransaction;
import com.customer.rewards.services.CustomerRewardsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerRewardsServiceImpl implements CustomerRewardsService {

    public static final int TWO_POINTS = 2;

    @Value("${rewards.single.point.amount}")
    private Integer singlePointAmount;

    @Value("${rewards.double.point.amount}")
    private Integer doublePointAmount;

    public Long calculate(List<CustomerTransaction> customerTransactions) {
        return customerTransactions.stream().map(customerTransaction -> calculateRewardPoints(customerTransaction))
                .collect(Collectors.summingLong(rewards -> rewards));
    }

    private Long calculateRewardPoints(CustomerTransaction customerTransaction) {
        long rewardPoints = 0;

        if (customerTransaction.getTransactionAmount() > singlePointAmount && customerTransaction.getTransactionAmount() <= doublePointAmount) {
            rewardPoints = Math.round(customerTransaction.getTransactionAmount() - singlePointAmount);
        } else if (customerTransaction.getTransactionAmount() > doublePointAmount) {
            rewardPoints = singlePointAmount + TWO_POINTS * Math.round(customerTransaction.getTransactionAmount() - doublePointAmount);
        }

        return rewardPoints;
    }
}
