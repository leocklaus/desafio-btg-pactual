package io.github.leocklaus.desafiobtg.domain.listener.dto;

import java.math.BigDecimal;

public record ClientTotalExpensesDTO(Long clientId, BigDecimal totalExpenses) {
}
