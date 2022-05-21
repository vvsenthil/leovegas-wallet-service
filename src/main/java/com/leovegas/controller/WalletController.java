package com.leovegas.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.leovegas.entity.Transaction;
import com.leovegas.entity.Wallet;
import com.leovegas.exception.WalletException;
import com.leovegas.model.User;
import com.leovegas.model.UserTransaction;
import com.leovegas.service.WalletService;
import com.leovegas.type.TransactionType;

@RestController
public class WalletController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WalletService walletService;

	@GetMapping(value = "/wallets/test")
	public String testApplication() throws WalletException {
		return "Hello Application started Successfully !!!";
	}
	
	/*
	 * To retrieve all available transactions for a specific userId
	 */
	@GetMapping(value = "/wallets/transactions/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Transaction> getAllTransactions(@PathVariable("userId") String userId) throws WalletException {
		logger.debug("Called WalletController.getAllTransactions with parameter userId={}", userId);
		return walletService.getTransactionsByUserId(userId);
	}

	/*
	 * To retrieve all available wallets from the system
	 */
	@GetMapping(value = "/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Wallet> getAllWallets() throws WalletException {
		logger.debug("Called WalletController.getAllWallets mthod");
		return walletService.getWallets();

	}

	/*
	 * To create new Transaction for a specific user
	 */
	@PostMapping(value = "/wallets/transactions/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Wallet createTransaction(@RequestBody UserTransaction transaction) throws WalletException {
		logger.debug(
				"Called WalletController.createTransaction with parameter UserTransaction and type of transaction is "
						+ transaction.getTransactionType());
		Wallet wallet = walletService.findByUserId(transaction.getUserId());

		if (wallet != null) {
			logger.debug("Called WalletController.createTransaction() and the requested user : "
					+ transaction.getUserId() + " is present");
			if (TransactionType.CREDIT.name().equalsIgnoreCase(transaction.getTransactionType())) {
				wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
				wallet = walletService.createTransaction(transaction, wallet);
			} else if (TransactionType.DEBIT.name().equalsIgnoreCase(transaction.getTransactionType())) {
				if (wallet.getBalance().compareTo(transaction.getAmount()) > 0) {
					wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
					wallet = walletService.createTransaction(transaction, wallet);
				} else {
					throw new WalletException("You have only £" + wallet.getBalance() + " Balance in your account "
							+ "unable to widthdraw Amount", HttpStatus.PAYMENT_REQUIRED);
				}

			}
		}
		return wallet;
	}

	/*
	 * To create new Wallet in the system
	 */
	@PostMapping(value = "/wallets/users/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Wallet createWallet(@RequestBody @Valid User user) throws WalletException {
		logger.debug(
				"Called WalletController.createWallet() with user information and user id is : " + user.getUserId());
		user.setTransactionType(TransactionType.CREDIT.name());
		Wallet createdUser = walletService.createWallet(user);
		return createdUser;
	}

	/*
	 * To Retrieves the Wallet details based on the input userId
	 */
	@GetMapping(value = "/wallets/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Wallet getWalletsByUserId(@PathVariable("userId") String userId) throws WalletException {
		logger.debug("Called WalletController.getWalletsByUserId() with user id : " + userId);
		Wallet wallet = walletService.findByUserId(userId);
		return wallet;
	}

	/*
	 * To remove all available Wallets details from the system
	 */
	@DeleteMapping("/wallets/users/remove")
	public ResponseEntity<String> removeWallets() throws WalletException {
		logger.debug("Called WalletController.removeWallets() ");
		walletService.removeWallets();
		return new ResponseEntity<String>("Wallets are removed Successfully!", HttpStatus.OK);
	}
}
