package com.cienet.interview.positionmanagement.rest;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cienet.interview.positionmanagement.entity.EquityPosition;
import com.cienet.interview.positionmanagement.entity.Transaction;
import com.cienet.interview.positionmanagement.exception.UnexpectedTransaction;
import com.cienet.interview.positionmanagement.service.EquityPositionService;

@RestController
public class PositionController {
	@Autowired
	private EquityPositionService service;
	
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/position")
	public EquityPostionREST showCurrentPosition() {
		EquityPostionREST resp = new EquityPostionREST();
		resp.setId(counter.incrementAndGet());
		
		// put mock data into DB to avoid empty result.
		prepareInitDataForRest();
		
		List<EquityPosition> resultList = service.listEquityPositions();
		
		resp.setEquityPositionList(resultList);
		return resp;
	}
	
	private void prepareInitDataForRest() {
		Transaction trans1 = new Transaction(1L, 1L, 1L, "REL", 50, "INSERT", "Buy");
		Transaction trans2 = new Transaction(2L, 2L, 1L, "ITC", 40, "INSERT", "Sell");
		
		try {
			service.acceptTransaction(trans1);
			service.acceptTransaction(trans2);
			service.processTransaction();
		} catch (UnexpectedTransaction e) {
			e.printStackTrace();
		}
	}
}
