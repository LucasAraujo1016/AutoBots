package com.autobots.automanager.DTO;

import java.util.Optional;
import java.util.Set;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;

public record AtualizarVendaDTO(
    Optional<String> indentificacao,
    Optional<Usuario> cliente,
    Optional<Usuario> funcionario,
    Optional<Set<Mercadoria>> mercadorias,
    Optional<Set<Servico>> servicos,
    Optional<Veiculo> veiculo
) {
} 
