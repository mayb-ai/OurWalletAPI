package com.mayb.api.dto;

import java.math.BigDecimal;

public record DashboardResponse(
        BigDecimal balance,      // Saldo
        BigDecimal totalIncome,  // Total Receitas
        BigDecimal totalExpense  // Total Despesas
) {}