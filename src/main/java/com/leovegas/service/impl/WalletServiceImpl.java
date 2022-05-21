package com.leovegas.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.leovegas.entity.Transaction;
import com.leovegas.entity.Wallet;
import com.leovegas.exception.WalletException;
import com.leovegas.model.User;
import com.leovegas.model.UserTransaction;
import com.leovegas.repository.TransactionRepository;
import com.leovegas.repository.WalletRepository;
import com.leovegas.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	/*
	 * To Retrieve Wallet for a specific userId
	 */
	@Override
	public Wallet findByUserId(String userId) throws WalletException {
		logger.debug("Called WalletServiceImpl.findByUserId() with parameter userId: ", userId);
		Wallet userExist = walletRepository.findByUserId(userId);
		if (userExist != null) {
			return walletRepository.findByUserId(userId);
		} else {
			logger.debug("Called WalletServiceImpl.findByUserId ", userId + " not present in the system");
			throw new WalletException("User Id " + userId + " is not present, Please provide correct user Id",
					HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Wallet createWallet(User user) throws WalletException {
		logger.debug("Called WalletServiceImpl.createWallet() with user details ");
		Wallet userExist = walletRepository.findByUserId(user.getUserId());
		if (userExist != null) {
			logger.debug("Called WalletServiceImpl.createWallet() the requested user " + user.getUserId()
					+ " is already present in the system");
			throw new WalletException(
					"User Id " + userExist.getUserId() + " Already present, Please create with new user Id",
					HttpStatus.CONFLICT);
		} else {
			logger.debug("Called WalletServiceImpl.createWallet() the requested user " + user.getUserId()
					+ " is not present in the system, so creating new wallet");
			try {
				userExist = walletRepository.save(new Wallet(user.getWalletId(), user.getUserId(), user.getBalance(),
						user.getUpdatedBy(), Arrays.asList(new Transaction(user.getUserId(), user.getTransId(),
								user.getBalance(), user.getTransactionType(), user.getUpdatedBy()))));
			} catch (Exception e) {
				logger.error("Called WalletServiceImpl.createWallet() the requested Transaction Id  "
						+ user.getTransId() + " is already present");
				throw new WalletException("Transaction Id " + user.getTransId()
						+ " Already present, Please create with new Transaction Id", HttpStatus.CONFLICT);
			}
			return userExist;
		}
	}

	@Override
	public List<Transaction> getTransactionsByUserId(String userId) {
		logger.debug("Called WalletServiceImpl.getTransactionsByUserId() with userId " + userId);
		List<Transaction> transactions = transactionRepository.findByUserId(userId);
		return transactions;
	}

	@Override
	public Wallet createTransaction(UserTransaction userTransaction, Wallet wallet) throws WalletException {
		logger.debug("Called WalletServiceImpl.createTransaction() to create new transaction ");
		try {
			List<Transaction> transactions = getTransactionsByUserId(wallet.getUserId());
			if (null != transactions) {
				logger.debug("Called WalletServiceImpl.createTransaction() and the requested user " + wallet.getUserId()
						+ " is exist");
				transactions.add(new Transaction(userTransaction.getUserId(), userTransaction.getTransId(),
						userTransaction.getAmount(), userTransaction.getTransactionType(),
						userTransaction.getUpdatedBy()));
			} else {
				logger.debug("Called WalletServiceImpl.createTransaction() and the requested user " + wallet.getUserId()
						+ " is not exist");
				transactions = Arrays.asList(new Transaction(userTransaction.getUserId(), userTransaction.getTransId(),
						userTransaction.getAmount(), userTransaction.getTransactionType(),
						userTransaction.getUpdatedBy()));
			}

			wallet = walletRepository.save(new Wallet(wallet.getWalletId(), wallet.getUserId(), wallet.getBalance(),
					wallet.getUpdatedBy(), transactions));
		} catch (Exception e) {
			logger.error("Called WalletServiceImpl.createTransaction() Transaction Id " + userTransaction.getTransId()
					+ " is Already present");
			throw new WalletException("Transaction Id " + userTransaction.getTransId()
					+ " Already present, Please create with new Transaction Id", HttpStatus.CONFLICT);
		}

		return wallet;
	}

	@Override
	public List<Wallet> getWallets() {
		logger.error("Called WalletServiceImpl.getWallets() is called ");
		List<Wallet> wallets = walletRepository.findAll();
		return wallets;
	}

	@Override
	public void removeWallets() {
		walletRepository.deleteAll();
	}
}
