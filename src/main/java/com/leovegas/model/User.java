package com.leovegas.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User {
	private Long walletId;

	@NotBlank(message = "Transaction Id should not be blank")
	@NotNull(message = "Transaction Id should not be null")
	private String transId;

	@NotBlank(message = "userId should not be blank")
	@NotNull(message = "userId should not be null")
	private String userId;
	private BigDecimal balance;
	private String updatedBy;
	private String transactionType;

	public User() {
	}

	public User(Long walletId, String transId, String userId, BigDecimal balance, String updatedBy,
			String transactionType) {
		super();
		this.walletId = walletId;
		this.transId = transId;
		this.userId = userId;
		this.balance = balance;
		this.updatedBy = updatedBy;
		this.transactionType = transactionType;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
