package com.mayb.api.service;

import com.mayb.api.entity.Transaction;
import com.mayb.api.entity.TransactionType;
import com.mayb.api.entity.User;
import com.mayb.api.repository.TransactionRepository;
import com.mayb.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.mayb.api.dto.DashboardResponse;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional // <--- A Mágica! Garante a segurança da operação no banco.
    public Transaction createTransaction(Transaction transaction) {

        if(transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Valor da transação deve ser acima de 0");
        // Futuro: Aqui verificaremos se o saldo é suficiente antes de salvar!
        }

        //Busca o usuário com todos os dados
        User userReal = userRepository.findById(transaction.getUser().getId()).orElseThrow(() -> new RuntimeException("Usuário não existe!"));

        //Aqui nós pegamos o objeto userReal e vinculamos no atributo user dentro do transaction, criando uma conexão no bd.
        transaction.setUser(userReal);

        //verificamos ser o id da familia é diferente de nulo para então adicionarmos a transação para aquela familia
        if(userReal.getFamily() != null){
            transaction.setFamily(userReal.getFamily());
        }

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(UUID id, Transaction transactionUpdated){
        Transaction transactionCurrent = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        transactionCurrent.setDescription(transactionUpdated.getDescription());
        transactionCurrent.setAmount(transactionUpdated.getAmount());
        transactionCurrent.setType(transactionUpdated.getType());
        transactionCurrent.setCategory(transactionUpdated.getCategory());
        transactionCurrent.setDate(transactionUpdated.getDate());

        return transactionRepository.save(transactionCurrent);
    }

    public List<Transaction> findAllTransactions(UUID userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public DashboardResponse getDashboard(UUID userId){
        // Pede a soma das RECEITAS direto pro banco
        BigDecimal income = transactionRepository.calculateTotal(userId, TransactionType.INCOME);

        // Pede a soma das DESPESAS direto pro banco
        BigDecimal expense = transactionRepository.calculateTotal(userId, TransactionType.EXPENSE);

        // Faz a subtração simples aqui
        BigDecimal balance = income.subtract(expense);

        // Retorna a caixinha pronta (DTO)
        return new DashboardResponse(balance, income, expense);
    }

    public void deleteTransaction(UUID id){
        //vê se existe
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new RuntimeException("Transação não encontrada."));

        //apaga do banco
        transactionRepository.delete(transaction);
    }
}
