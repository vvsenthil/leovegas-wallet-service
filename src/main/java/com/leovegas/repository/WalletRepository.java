package com.leovegas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leovegas.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	Wallet findByUserId(String userId);
}
