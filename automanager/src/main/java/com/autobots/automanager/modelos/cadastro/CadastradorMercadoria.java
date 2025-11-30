package com.autobots.automanager.modelos.cadastro;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entidades.Mercadoria;

import java.util.Date;

@Component
public class CadastradorMercadoria {
    public Mercadoria cadastrarMercadoria(Mercadoria mercadoria){
        Mercadoria setMercadoria = new Mercadoria();
        setMercadoria.setNome(mercadoria.getNome());
        setMercadoria.setValor(mercadoria.getValor());
        setMercadoria.setQuantidade(mercadoria.getQuantidade());
        setMercadoria.setCadastro(new Date());
        setMercadoria.setFabricao(new Date());
        setMercadoria.setValidade(new Date());
        if (mercadoria.getDescricao() != null) {
            setMercadoria.setDescricao(mercadoria.getDescricao());
        }

        return setMercadoria;
    }

    public Mercadoria cadastrarMercadoria (MercadoriaDTO mercadoria) {
        Mercadoria mercadoriaNova = new Mercadoria();
        mercadoriaNova.setValidade(new Date());
        mercadoriaNova.setFabricao(new Date());
        mercadoriaNova.setCadastro(new Date());
        mercadoriaNova.setNome(mercadoria.nome());
        mercadoriaNova.setQuantidade(mercadoria.quantidade());
        mercadoriaNova.setValor(mercadoria.valor());
        if (mercadoria.descricao().isPresent()) {
            mercadoriaNova.setDescricao(mercadoria.descricao().get());
        }

        return mercadoriaNova;
    }
}