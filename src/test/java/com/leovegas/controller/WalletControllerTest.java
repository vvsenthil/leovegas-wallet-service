package com.leovegas.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
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
import com.leovegas.service.WalletService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WalletControllerTest {

	@Autowired
	private WalletService walletService;

	private Transaction transactionOne;
	private Transaction transactionTwo;
	private Wallet walletOne;
	private Wallet walletTwo;
	private User userOne;
	private User userTwo;

	@BeforeEach
	void setUp() throws Exception {
		transactionOne = new Transaction(1L, "user1", "tran1", new BigDecimal(10), "CREDIT", "Senthil");
		transactionTwo = new Transaction(2L, "user2", "tran2", new BigDecimal(100), "CREDIT", "Senthil");

		walletOne = new Wallet(1L, "user1", new BigDecimal(10), "Senthil", Arrays.asList(transactionOne));
		walletTwo = new Wallet(2L, "user2", new BigDecimal(100), "Senthil", Arrays.asList(transactionTwo));

		userOne = new User(1L, "tran1", "user1", new BigDecimal(10), "Senthil", "CREDIT");
		userTwo = new User(2L, "tran2", "user2", new BigDecimal(100), "Senthil", "CREDIT");
	}

	@Test
	void testCreateWallet_1() throws Exception {
		Wallet dbWallet = walletService.createWallet(userOne);
		assertNotNull(dbWallet);
		assertEquals(walletOne.getUserId(), dbWallet.getUserId());
		walletService.removeWallets();
	}

	@Test
	void testCreateWallet_2() throws Exception {
		Wallet dbWallet = walletService.createWallet(userTwo);
		assertNotNull(dbWallet);
		assertEquals(walletTwo.getUserId(), dbWallet.getUserId());
		walletService.removeWallets();
	}

	@Test
	void testCreateWalletNonUniqueUserId() throws Exception {
		Wallet dbWallet = walletService.createWallet(userOne);
		assertNotNull(dbWallet);
		Throwable exception = assertThrows(WalletException.class, () -> {
			walletService.createWallet(userOne);
		});
		assertEquals("User Id user1 Already present, Please create with new user Id", exception.getMessage());
		walletService.removeWallets();
	}

	@Test
	void testGetWallet() throws Exception {
		walletService.createWallet(userOne);
		Wallet dbWallet = walletService.findByUserId("user1");
		assertNotNull(dbWallet);
		assertEquals(walletOne.getUserId(), dbWallet.getUserId());
		walletService.removeWallets();
	}

	@Test
	void testGetAllWallets() throws Exception {
		walletService.createWallet(userOne);
		walletService.createWallet(userTwo);
		List<Wallet> wallets = walletService.getWallets();
		assertNotNull(wallets);
		assertEquals(wallets.size(), 2);
		walletService.removeWallets();
	}

}
