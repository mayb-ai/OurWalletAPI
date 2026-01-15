package com.mayb.api.service;

import com.mayb.api.entity.Transaction;
import com.mayb.api.entity.User;
import com.mayb.api.repository.TransactionRepository;
import com.mayb.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
}
