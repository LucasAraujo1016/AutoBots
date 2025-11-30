package com.autobots.automanager.modelos.atualizar;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Mercadoria;

@Component
public class AtualizadorMercadoria {
    public Mercadoria atualizarMercadoria(Mercadoria mercadoria, Mercadoria setMercadoria) {
        mercadoria.setNome(setMercadoria.getNome());
        mercadoria.setDescricao(setMercadoria.getDescricao());
        mercadoria.setValor(setMercadoria.getValor());
        mercadoria.setQuantidade(setMercadoria.getQuantidade());
        return mercadoria;
    }
}
