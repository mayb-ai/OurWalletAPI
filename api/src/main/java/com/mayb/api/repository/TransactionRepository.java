package com.mayb.api.repository;

import com.mayb.api.entity.Transaction;
import com.mayb.api.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction, UUID> {
    List<Transaction> findAllByUserId(UUID userId);

    //diz para o próprio banco de dados que ele precisa fazer a soma lá e retornar apenas o resultado
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.id = :userId AND t.type = :type") //Some (SUM) o valor (amount) de todas as transações (Transaction t) onde o usuário é esse (:userId) e o tipo é esse (:type). Se não tiver nada, me devolva zero (COALESCE... 0).
    BigDecimal calculateTotal(UUID userId, TransactionType type);
}
