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

	Transaction transactionOne;
	Transaction transactionTwo;
	Wallet walletOne;
	Wallet walletTwo;
	User userOne;
	User userTwo;

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
		walletService.removeWallets();
		assertNotNull(dbWallet);
		assertEquals(walletOne.getUserId(), dbWallet.getUserId());

	}

	@Test
	void testCreateWallet_2() throws Exception {
		Wallet dbWallet = walletService.createWallet(userTwo);
		walletService.removeWallets();
		assertNotNull(dbWallet);
		assertEquals(walletTwo.getUserId(), dbWallet.getUserId());
	}

	@Test
	void testCreateWalletNonUniqueUserId() throws Exception {
		Wallet dbWallet = walletService.createWallet(userOne);
		assertNotNull(dbWallet);
		Throwable exception = assertThrows(WalletException.class, () -> {
			walletService.createWallet(userOne);
		});
		walletService.removeWallets();
		assertEquals("User Id user1 Already present, Please create with new user Id", exception.getMessage());

	}

	@Test
	void testGetWallet() throws Exception {
		walletService.createWallet(userOne);
		Wallet dbWallet = walletService.findByUserId("user1");
		walletService.removeWallets();
		assertNotNull(dbWallet);
		assertEquals(walletOne.getUserId(), dbWallet.getUserId());

	}

	@Test
	void testGetAllWallets() throws Exception {
		walletService.createWallet(userOne);
		walletService.createWallet(userTwo);
		List<Wallet> wallets = walletService.getWallets();
		walletService.removeWallets();
		assertNotNull(wallets);
		assertEquals(wallets.size(), 2);

	}

}
