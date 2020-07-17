package com.customer.rewards.repositories;

import com.customer.rewards.domain.CustomerTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface CustomerTransactionRepository extends CrudRepository<CustomerTransaction, Long> {

    List<CustomerTransaction> findByCustomerIdAndTransactionDateBetween(Long customerId, Date fromDate, Date toDate);

    List<CustomerTransaction> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
}