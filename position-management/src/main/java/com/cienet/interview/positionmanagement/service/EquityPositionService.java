package com.cienet.interview.positionmanagement.service;

import java.util.List;
import java.util.Map;

import com.cienet.interview.positionmanagement.entity.EquityPosition;
import com.cienet.interview.positionmanagement.entity.Transaction;
import com.cienet.interview.positionmanagement.exception.UnexpectedTransaction;

public interface EquityPositionService {

	public void acceptTransaction(Transaction transaction) throws UnexpectedTransaction;

	public void processTransaction() throws UnexpectedTransaction;

	public List<EquityPosition> listEquityPositions();

}
