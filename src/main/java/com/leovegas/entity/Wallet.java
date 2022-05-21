package com.leovegas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Wallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long walletId;

	@NotNull(message = "User id is Mandatory field ")
	private String userId;

	@Min(0)
	private BigDecimal balance;
	private String updatedBy;
	@OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL)
	private List<Transaction> transactions;

	public Wallet() {
	}

	public Wallet(Long walletId, @NotNull(message = "User id is Mandatory field ") String userId,
			@Min(0) BigDecimal balance, String updatedBy, List<Transaction> transactions) {
		super();
		this.walletId = walletId;
		this.userId = userId;
		this.balance = balance;
		this.updatedBy = updatedBy;
		this.transactions = transactions;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
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

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
