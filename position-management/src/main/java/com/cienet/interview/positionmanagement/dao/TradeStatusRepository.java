package com.cienet.interview.positionmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cienet.interview.positionmanagement.entity.EquityPosition;
import com.cienet.interview.positionmanagement.entity.TradeStatus;

@Repository
public interface TradeStatusRepository extends JpaRepository<TradeStatus, Long> {

	@Query("select t.securityCode as equity, sum(t.quantity) as position from TradeStatus t group by t.securityCode")
	List<EquityPosition> listEquityPosition();
}
