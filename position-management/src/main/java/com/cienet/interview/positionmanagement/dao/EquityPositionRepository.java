package com.cienet.interview.positionmanagement.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cienet.interview.positionmanagement.entity.EquityPosition;

@Repository
public interface EquityPositionRepository extends JpaRepository<EquityPosition, Long> {
	Optional<EquityPosition> findByEquity(String equity);
}
