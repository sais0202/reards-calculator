package com.customer.rewards.services;

import com.customer.rewards.domain.CustomerTransaction;
import com.customer.rewards.services.impl.CustomerRewardsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CustomerRewardsServiceImpl.class})
@TestPropertySource(locations = "classpath:rewards-threshold.properties")
public class CustomerRewardsServiceTests {

    @Resource
    private CustomerRewardsService customerRewardsService;

    @Test
    public void returnsZeroRewardPointsIfNoCustomerTransactions() {

        List<CustomerTransaction> customerTransactions = new ArrayList<>();

        Long rewardPoints = customerRewardsService.calculate(customerTransactions);

        Assertions.assertEquals(0, rewardPoints);
    }

    @Test
    public void returnsRewardPointsForGivenCustomerTransactions() {

        List<CustomerTransaction> customerTransactions = new ArrayList<>();
        customerTransactions.add(getCustomerTransaction(101, 3701, 45.20));
        customerTransactions.add(getCustomerTransaction(101, 3702, 68.45));
        customerTransactions.add(getCustomerTransaction(101, 3703, 140));

        Long rewardPoints = customerRewardsService.calculate(customerTransactions);

        Assertions.assertEquals(148, rewardPoints);
    }

    private CustomerTransaction getCustomerTransaction(long customerId, long transactionId, double amount) {
        CustomerTransaction customerTransaction = new CustomerTransaction();
        customerTransaction.setCustomerId(customerId);
        customerTransaction.setId(transactionId);
        customerTransaction.setTransactionAmount(amount);
        customerTransaction.setTransactionDate(new Date());

        return customerTransaction;
    }
}
