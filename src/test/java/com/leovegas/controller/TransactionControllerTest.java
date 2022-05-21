package com.leovegas.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leovegas.entity.Transaction;
import com.leovegas.entity.Wallet;
import com.leovegas.exception.WalletException;
import com.leovegas.model.User;
import com.leovegas.model.UserTransaction;
import com.leovegas.service.WalletService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

	@Autowired
	private WalletService walletService;

	User userOne;
	User userTwo;
	UserTransaction user1TransactionOne;
	UserTransaction user1TransactionTwo;
	Wallet walletNonUniqueTransaction = null;

	@BeforeEach
	void setUp() throws Exception {
		user1TransactionOne = new UserTransaction("user1", "tran3", new BigDecimal(100.00), "CREDIT", "Senthil");
		user1TransactionTwo = new UserTransaction("user1", "tran4", new BigDecimal(10.00), "DEBIT", "Senthil");
		userOne = new User(1L, "tran1", "user1", new BigDecimal(10.00), "Senthil", "CREDIT");
		userTwo = new User(2L, "tran2", "user2", new BigDecimal(100.00), "Senthil", "CREDIT");
	}

	@Test
	void testCreateCreditTransactionForUser_1() throws WalletException {
		Wallet dbWallet = walletService.createWallet(userOne);
		assertNotNull(dbWallet);
		dbWallet.setBalance(dbWallet.getBalance().add(user1TransactionOne.getAmount()));
		dbWallet = walletService.createTransaction(user1TransactionOne, dbWallet);
		walletService.removeWallets();
		assertNotNull(dbWallet);
		assertEquals(dbWallet.getBalance(), new BigDecimal(110.00));

	}

	@Test
	void testCreateDebitTransactionForUser_2() throws WalletException {
		userOne.setTransactionType("DEBIT");
		Wallet dbWallet = walletService.createWallet(userOne);
		assertNotNull(dbWallet);
		dbWallet.setBalance(dbWallet.getBalance().subtract(user1TransactionOne.getAmount()));
		dbWallet = walletService.findByUserId("user1");
		assertNotNull(dbWallet);
		dbWallet.setBalance(dbWallet.getBalance().add(user1TransactionTwo.getAmount()));
		dbWallet = walletService.createTransaction(user1TransactionTwo, dbWallet);
		walletService.removeWallets();
		assertNotNull(dbWallet);
		assertEquals(dbWallet.getBalance(), dbWallet.getBalance());

	}

	@Test
	void testCreateNonUniqueDebitTransaction() throws Exception {
		userOne.setTransactionType("DEBIT");
		user1TransactionOne.setTransId("tran1");
		walletNonUniqueTransaction = walletService.createWallet(userOne);
		assertNotNull(walletNonUniqueTransaction);
		walletNonUniqueTransaction
				.setBalance(walletNonUniqueTransaction.getBalance().subtract(user1TransactionOne.getAmount()));

		Throwable exception = assertThrows(WalletException.class, () -> {
			walletService.createTransaction(user1TransactionOne, walletNonUniqueTransaction);
		});
		walletService.removeWallets();
		assertEquals("Transaction Id tran1 Already present, Please create with new Transaction Id",
				exception.getMessage());

	}

	@Test
	void testCreateNonUniqueCreditTransaction() throws Exception {
		userOne.setTransactionType("CREDIT");
		user1TransactionOne.setTransId("tran1");
		walletNonUniqueTransaction = walletService.createWallet(userOne);
		assertNotNull(walletNonUniqueTransaction);
		walletNonUniqueTransaction
				.setBalance(walletNonUniqueTransaction.getBalance().add(user1TransactionTwo.getAmount()));

		Throwable exception = assertThrows(WalletException.class, () -> {
			walletService.createTransaction(user1TransactionOne, walletNonUniqueTransaction);
		});
		walletService.removeWallets();
		assertEquals("Transaction Id tran1 Already present, Please create with new Transaction Id",
				exception.getMessage());

	}

	@Test
	void testGetAllTransactions() throws WalletException {
		Wallet dbWallet = walletService.createWallet(userOne);
		assertNotNull(dbWallet);
		// For Credit Transaction
		dbWallet.setBalance(dbWallet.getBalance().add(user1TransactionOne.getAmount()));
		dbWallet = walletService.createTransaction(user1TransactionOne, dbWallet);
		assertNotNull(dbWallet);
		// For Debit Transaction
		dbWallet.setBalance(dbWallet.getBalance().subtract(user1TransactionOne.getAmount()));
		dbWallet = walletService.createTransaction(user1TransactionTwo, dbWallet);
		List<Transaction> transactions = walletService.getTransactionsByUserId(dbWallet.getUserId());
		walletService.removeWallets();
		assertNotNull(transactions);
		assertEquals(3, transactions.size());

	}

}
