package com.autobots.automanager.modelos.atualizar;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Servico;

@Component
public class AtualizadorServico {
    public Servico atualizarServico(Servico servico, Servico setServico) {
        servico.setNome(setServico.getNome());
        servico.setDescricao(setServico.getDescricao());
        servico.setNome(setServico.getNome());
        return servico;
    }
}
