package com.autobots.automanager.DTO;

import java.util.Optional;

public record CredencialDTO(
        Optional<String> nomeUsuario,
        Optional<String> senha,
        Optional<Long> codigo
) {
}