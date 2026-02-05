package com.mayb.api.controller;

import com.mayb.api.entity.Transaction;
import com.mayb.api.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mayb.api.dto.DashboardResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions(@RequestParam UUID userId) {
        return transactionService.findAllTransactions(userId);
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard(@RequestParam UUID userId){
        return transactionService.getDashboard((userId));
    }

    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id){
        transactionService.deleteTransaction(id);

        // Retorna status 204 (Sucesso, mas sem conteúdo para mostrar)
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable UUID id, @RequestBody Transaction transaction){
        return transactionService.updateTransaction(id, transaction);
    }
}