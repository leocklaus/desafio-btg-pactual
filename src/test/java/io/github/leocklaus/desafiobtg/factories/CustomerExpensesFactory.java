package io.github.leocklaus.desafiobtg.factories;

import io.github.leocklaus.desafiobtg.domain.listener.dto.ClientTotalExpensesDTO;

import java.math.BigDecimal;

public class CustomerExpensesFactory {

    public static ClientTotalExpensesDTO with200Reais(){
        return new ClientTotalExpensesDTO(1L, BigDecimal.valueOf(200));
    }

}
