package com.leovegas.service;

import java.util.List;

import com.leovegas.entity.Transaction;
import com.leovegas.entity.Wallet;
import com.leovegas.exception.WalletException;
import com.leovegas.model.User;
import com.leovegas.model.UserTransaction;

public interface WalletService {

	public List<Transaction> getTransactionsByUserId(String userId);

	public Wallet findByUserId(String userId) throws WalletException;

	public Wallet createWallet(User user) throws WalletException;

	public Wallet createTransaction(UserTransaction userTransaction, Wallet wallet) throws WalletException;

	public List<Wallet> getWallets();

	public void removeWallets();
}
