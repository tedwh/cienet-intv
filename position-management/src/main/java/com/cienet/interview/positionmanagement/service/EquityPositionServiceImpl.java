package com.cienet.interview.positionmanagement.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cienet.interview.positionmanagement.dao.TradeStatusRepository;
import com.cienet.interview.positionmanagement.dao.TransactionRepository;
import com.cienet.interview.positionmanagement.entity.EquityPosition;
import com.cienet.interview.positionmanagement.entity.TradeStatus;
import com.cienet.interview.positionmanagement.entity.Transaction;
import com.cienet.interview.positionmanagement.exception.UnexpectedTransaction;

@Service
public class EquityPositionServiceImpl implements EquityPositionService {
	public static final String TRADE_ACTION_INSERT = "INSERT";
	public static final String TRADE_ACTION_UPDATE = "UPDATE";
	public static final String TRADE_ACTION_CANCEL = "CANCEL";

	public static final String TRANSACTION_TYPE_BUY = "Buy";
	public static final String TRANSACTION_TYPE_SELL = "Sell";

	@Resource
	private TradeStatusRepository tradeStatusRepository;
	@Resource
	private TransactionRepository transactionRepository;

	/**
	 * Main service method, stand by to accept transactions and store into Database.
	 */
	@Override
	public void acceptTransaction(Transaction transaction) throws UnexpectedTransaction {

		if (transaction.getTransactionId() != null && transaction.getTradeId() != null
				&& transaction.getVersion() != null && transaction.getQuantity() != null
				&& transaction.getSecurityCode() != null && transaction.getTradeActionType() != null
				&& transaction.getTransactionType() != null) {
			if (TRADE_ACTION_INSERT.equals(transaction.getTradeActionType()) && transaction.getVersion() != 1) {
				throw new UnexpectedTransaction("Invalid transaction, \"INSERT\" trade with version not equals to 1: "
						+ transaction.toString());
			}
			transactionRepository.save(transaction);
		} else {
			throw new UnexpectedTransaction("Invalid transaction: " + transaction.toString());
		}
	}

	/**
	 * Main service method, batch process the accepted transactions, update
	 * trade_status table
	 */
	@Override
	public void processTransaction() throws UnexpectedTransaction {
		preProcess();

		List<Transaction> tranListInsert = transactionRepository
				.findByTradeActionTypeAndProcessedOrderByTransactionId(TRADE_ACTION_INSERT, Boolean.FALSE);
		for (Transaction transaction : tranListInsert) {
			processInsertTrade(transaction);
		}

		List<Transaction> tranListUpdate = transactionRepository
				.findByTradeActionTypeAndProcessedOrderByTransactionId(TRADE_ACTION_UPDATE, Boolean.FALSE);
		for (Transaction transaction : tranListUpdate) {
			processUpdateTrade(transaction);
		}

		List<Transaction> tranListCancel = transactionRepository
				.findByTradeActionTypeAndProcessedOrderByTransactionId(TRADE_ACTION_CANCEL, Boolean.FALSE);
		for (Transaction transaction : tranListCancel) {
			processCancelTrade(transaction);
		}

		postProcess();
	}

	@Transactional
	protected TradeStatus processInsertTrade(Transaction transaction) throws UnexpectedTransaction {

		TradeStatus tradeStatus = new TradeStatus();
		tradeStatus.setTradeId(transaction.getTradeId());
		tradeStatus.setSecurityCode(transaction.getSecurityCode());
		Integer quantiy = transaction.getQuantity();
		if (TRANSACTION_TYPE_SELL.equals(transaction.getTransactionType())) {
			quantiy = -1 * quantiy;
		}
		tradeStatus.setQuantity(quantiy);

		tradeStatus.setLastTradeVersion(transaction.getVersion());
		tradeStatus.setLastTransactionId(transaction.getTransactionId());
		tradeStatus.setLastTransactionType(transaction.getTransactionType());
		tradeStatus.setLastTradeActionType(transaction.getTradeActionType());

		transaction.setProcessed(Boolean.TRUE);
		transactionRepository.save(transaction);
		return tradeStatusRepository.save(tradeStatus);

	}

	@Transactional
	public void processUpdateTrade(Transaction transaction) throws UnexpectedTransaction {

		Optional<TradeStatus> trade = tradeStatusRepository.findById(transaction.getTradeId());
		if (!trade.isPresent()) {
			return;
		}
		TradeStatus tradeStatus = trade.get();
		Integer quantity = transaction.getQuantity();
		if (TRANSACTION_TYPE_BUY.equals(transaction.getTransactionType())) {
			tradeStatus.setQuantity(quantity);
		} else if (TRANSACTION_TYPE_SELL.equals(transaction.getTransactionType())) {
			tradeStatus.setQuantity(-1 * quantity);
		}

		// the following three field is stored to be used in the future, for
		// non-sequential transactions handling support.
		tradeStatus.setLastTradeVersion(transaction.getVersion());
		tradeStatus.setLastTransactionId(transaction.getTransactionId());
		tradeStatus.setLastTransactionType(transaction.getTransactionType());
		tradeStatus.setLastTradeActionType(transaction.getTradeActionType());

		transaction.setProcessed(Boolean.TRUE);
		transactionRepository.save(transaction);
		tradeStatusRepository.save(tradeStatus);

	}

	@Transactional
	public void processCancelTrade(Transaction transaction) throws UnexpectedTransaction {
		Optional<TradeStatus> trade = tradeStatusRepository.findById(transaction.getTradeId());
		if (!trade.isPresent()) {
			return;
		}

		TradeStatus tradeStatus = trade.get();
		if (tradeStatus.getLastTradeVersion() > transaction.getVersion()) {
			throw new UnexpectedTransaction(
					"Found a \"CANCEL\" transaction which is not the end transaction of a trade: "
							+ transaction.toString());
		}

		tradeStatus.setQuantity(0);

		tradeStatus.setLastTradeVersion(transaction.getVersion());
		tradeStatus.setLastTransactionId(transaction.getTransactionId());
		tradeStatus.setLastTransactionType(transaction.getTransactionType());
		tradeStatus.setLastTradeActionType(transaction.getTradeActionType());

		transaction.setProcessed(Boolean.TRUE);
		transactionRepository.save(transaction);
		tradeStatusRepository.save(tradeStatus);
	}

	/**
	 * Query trade_status table, summarize and return the equity position result.
	 */
	@Override
	public List<EquityPosition> listEquityPositions() {
		return tradeStatusRepository.listEquityPosition();
	}

	protected void preProcess() throws UnexpectedTransaction {

	}

	protected void postProcess() throws UnexpectedTransaction {

	}

	// Just for cleaning data in JUnit Test.
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void cleanup() {
		em.createNativeQuery("delete from Transaction").executeUpdate();
		em.createNativeQuery("delete from Trade_Status").executeUpdate();
	}
}
