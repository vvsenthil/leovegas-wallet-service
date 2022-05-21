package com.leovegas.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Transaction Id must be given and should be unique")
	@Column(name = "tranId", unique = true, nullable = false)
	private String tranId;

	private String userId;

	private BigDecimal amount;

	private String transactionType;

	private String updatedBy;

	public Transaction() {

	}

	public Transaction(long id, String userId, String tranId, BigDecimal amount, String transactionType,
			String updatedBy) {
		this.id = id;
		this.userId = userId;
		this.tranId = tranId;
		this.amount = amount;
		this.transactionType = transactionType;
		this.updatedBy = updatedBy;
	}

	public Transaction(String userId, String tranId, BigDecimal amount, String transactionType, String updatedBy) {
		this.userId = userId;
		this.tranId = tranId;
		this.amount = amount;
		this.transactionType = transactionType;
		this.updatedBy = updatedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
