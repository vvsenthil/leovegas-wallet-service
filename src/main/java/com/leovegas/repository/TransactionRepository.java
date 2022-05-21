package com.leovegas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leovegas.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByUserId(String userId);
}
