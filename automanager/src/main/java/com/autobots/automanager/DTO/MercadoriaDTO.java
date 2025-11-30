package com.autobots.automanager.DTO;

import java.util.Optional;

public record MercadoriaDTO(
        String nome,
        long quantidade,
        double valor,
        Optional<String> descricao
){
}