package com.cienet.interview.positionmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cienet.interview.positionmanagement.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByProcessedOrderByTransactionId(Boolean processed);
	
	List<Transaction> findByTradeActionTypeAndProcessedOrderByTransactionId(String tradeActionType, Boolean processed);
	
}