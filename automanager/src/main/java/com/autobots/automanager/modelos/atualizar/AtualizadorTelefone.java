package com.autobots.automanager.modelos.atualizar;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Telefone;

@Component
public class AtualizadorTelefone {
    public Telefone atualizarTelefone(Telefone telefone, Telefone setTelefone) {
        telefone.setDdd(setTelefone.getNumero());
        telefone.setNumero(setTelefone.getNumero());
        return telefone;
    }

}
