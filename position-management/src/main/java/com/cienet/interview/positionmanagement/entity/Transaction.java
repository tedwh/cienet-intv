package com.cienet.interview.positionmanagement.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Transaction {
	@Id
	private Long transactionId;
	private Long tradeId;
	private Long version;
	private String SecurityCode;
	private Integer quantity;
	private String tradeActionType;
	private String transactionType;
	private Boolean processed = Boolean.FALSE;
	private Date createTime;

	public Transaction() {
	}

	public Transaction(Long transactionId, Long tradeId, Long version, String securityCode, Integer quantity,
			String tradeActionType, String transactionType) {
		super();
		this.transactionId = transactionId;
		this.tradeId = tradeId;
		this.version = version;
		SecurityCode = securityCode;
		this.quantity = quantity;
		this.tradeActionType = tradeActionType;
		this.transactionType = transactionType;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public Long getVersion() {
		return version;
	}

	public String getSecurityCode() {
		return SecurityCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getTradeActionType() {
		return tradeActionType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionId(Long transactionID) {
		this.transactionId = transactionID;
	}

	public void setTradeId(Long tradeID) {
		this.tradeId = tradeID;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setTradeActionType(String tradeActionType) {
		this.tradeActionType = tradeActionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SecurityCode == null) ? 0 : SecurityCode.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((tradeActionType == null) ? 0 : tradeActionType.hashCode());
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Transaction other = (Transaction) obj;
		if (SecurityCode == null) {
			if (other.SecurityCode != null)
				return false;
		} else if (!SecurityCode.equals(other.SecurityCode))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (tradeActionType == null) {
			if (other.tradeActionType != null)
				return false;
		} else if (!tradeActionType.equals(other.tradeActionType))
			return false;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", tradeId=" + tradeId + ", version=" + version
				+ ", SecurityCode=" + SecurityCode + ", quantity=" + quantity + ", tradeActionType=" + tradeActionType
				+ ", transactionType=" + transactionType + "]";
	}
	
}
