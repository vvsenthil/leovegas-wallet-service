package com.leovegas.model;

import java.math.BigDecimal;

public class UserTransaction {
	private String userId;
	private String transId;
	private BigDecimal amount;
	private String transactionType;
	private String updatedBy;

	public UserTransaction(String userId, String transId, BigDecimal amount, String transactionType, String updatedBy) {
		super();
		this.userId = userId;
		this.transId = transId;
		this.amount = amount;
		this.transactionType = transactionType;
		this.updatedBy = updatedBy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
