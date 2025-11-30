package com.autobots.automanager.modelos.cadastro;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entitades.Mercadoria;

@Component
public class CadastradorMercadoria {
    public Mercadoria cadastMercadoria(Mercadoria mercadoria) {
        Mercadoria mercadoriaNova = new Mercadoria();
        mercadoriaNova.setNome(mercadoria.getNome());
        mercadoriaNova.setValor(mercadoria.getValor());
        mercadoriaNova.setQuantidade(mercadoria.getQuantidade());
        mercadoriaNova.setCadastro(new Date());
        mercadoriaNova.setFabricao(new Date());
        mercadoriaNova.setValidade(new Date());
        if(mercadoria.getDescricao() != null) {
            mercadoriaNova.setDescricao(mercadoria.getDescricao());
        }

        return mercadoriaNova;
    }

    public Mercadoria cadastrarMercadoria(MercadoriaDTO mercadoria) {
        Mercadoria mercadoriaNova = new Mercadoria();
        mercadoriaNova.setValidade(new Date());
        mercadoriaNova.setFabricao(new Date());
        mercadoriaNova.setCadastro(new Date());
        mercadoriaNova.setNome(mercadoria.nome());
        mercadoriaNova.setQuantidade(mercadoria.quantidade());
        mercadoriaNova.setValor(mercadoria.valor());
        if(mercadoria.descricao().isPresent()) {
            mercadoriaNova.setDescricao(mercadoria.descricao().get());
        }

        return mercadoriaNova;
    }
}
