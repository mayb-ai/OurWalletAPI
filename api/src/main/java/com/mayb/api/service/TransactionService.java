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

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public DashboardResponse getDashboard(UUID userId){
        // Busca todas as transações DESSE usuário
        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);

        // Inicializa os totais com ZERO
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;


        for(Transaction t : transactions){
            if(t.getType() == TransactionType.INCOME){
                income = income.add(t.getAmount());
            } else {
                expense = expense.add(t.getAmount());
            }
        }

        // Calcula o Saldo (Receita - Despesa)
        BigDecimal balance = income.subtract(expense);

        // Retorna a caixinha pronta (DTO)
        return new DashboardResponse(balance, income, expense);
    }
}
