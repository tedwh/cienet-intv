package com.cienet.interview.positionmanagement.rest;

import java.util.List;

import com.cienet.interview.positionmanagement.entity.EquityPosition;

public class EquityPostionREST {
	private long id;
	private List<EquityPosition> equityPositionList;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<EquityPosition> getEquityPositionList() {
		return equityPositionList;
	}
	public void setEquityPositionList(List<EquityPosition> equityPositionList) {
		this.equityPositionList = equityPositionList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((equityPositionList == null) ? 0 : equityPositionList.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquityPostionREST other = (EquityPostionREST) obj;
		if (equityPositionList == null) {
			if (other.equityPositionList != null)
				return false;
		} else if (!equityPositionList.equals(other.equityPositionList))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
