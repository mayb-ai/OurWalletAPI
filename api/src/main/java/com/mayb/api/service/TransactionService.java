package com.mayb.api.service;

import com.mayb.api.entity.Transaction;
import com.mayb.api.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional // <--- A Mágica! Garante a segurança da operação no banco.
    public Transaction createTransaction(Transaction transaction) {
        // Futuro: Aqui verificaremos se o saldo é suficiente antes de salvar!
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
