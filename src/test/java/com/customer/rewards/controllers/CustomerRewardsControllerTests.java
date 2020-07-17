package com.customer.rewards.controllers;

import com.customer.rewards.domain.CustomerTransaction;
import com.customer.rewards.model.CustomerRewards;
import com.customer.rewards.repositories.CustomerTransactionRepository;
import com.customer.rewards.services.CustomerRewardsService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CustomerRewardsController.class})
public class CustomerRewardsControllerTests {

    @Resource
    private CustomerRewardsController customerRewardsController;

    @MockBean
    private CustomerRewardsService customerRewardsService;

    @MockBean
    private CustomerTransactionRepository customerTransactionRepository;

    @Test
    public void returnsZeroCustomerRewardsWhenNoTransactions() {
        long customerId = 1001;

        List<CustomerTransaction> transcactions = Lists.newArrayList();
        Mockito.when(customerTransactionRepository.findByCustomerIdAndTransactionDateBetween(Mockito.anyLong(),
                Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(transcactions);
        Mockito.when(customerRewardsService.calculate(transcactions)).thenReturn(0L);

        CustomerRewards customerRewards = customerRewardsController.getCustomerRewards(customerId);

        Assertions.assertNotNull(customerRewards);
        Assertions.assertEquals(0, customerRewards.getCurrentMonthRewardPoints());
        Assertions.assertEquals(0, customerRewards.getPreviousMonthRewardPoints());
        Assertions.assertEquals(0, customerRewards.getThirdMonthRewardPoints());
        Assertions.assertEquals(0, customerRewards.getTotalRewards());
    }

    @Test
    public void returnsCustomerRewardsForGivenCustomerId() {

        long customerId = 1001;

        List<CustomerTransaction> transcactions = Lists.newArrayList(getCustomerTransaction(customerId, 1, 55));
        Mockito.when(customerTransactionRepository.findByCustomerIdAndTransactionDateBetween(Mockito.anyLong(),
                Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(transcactions);
        Mockito.when(customerRewardsService.calculate(transcactions)).thenReturn(5L);

        CustomerRewards customerRewards = customerRewardsController.getCustomerRewards(customerId);

        Assertions.assertNotNull(customerRewards);
        Assertions.assertEquals(5, customerRewards.getCurrentMonthRewardPoints());
        Assertions.assertEquals(5, customerRewards.getPreviousMonthRewardPoints());
        Assertions.assertEquals(5, customerRewards.getThirdMonthRewardPoints());
        Assertions.assertEquals(15, customerRewards.getTotalRewards());

        Mockito.verify(customerTransactionRepository, Mockito.times(3)).findByCustomerIdAndTransactionDateBetween(Mockito.anyLong(),
                Mockito.any(Date.class), Mockito.any(Date.class));
        Mockito.verify(customerRewardsService, Mockito.times(3)).calculate(transcactions);
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
