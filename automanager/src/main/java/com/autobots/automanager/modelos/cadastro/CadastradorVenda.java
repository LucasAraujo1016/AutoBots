package com.autobots.automanager.modelos.cadastro;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;

@Component
public class CadastradorVenda {
    
    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    public Venda cadastrarVenda(Venda venda) {
        Venda vendaNova = new Venda();
        vendaNova.setCadastro(new Date());
        vendaNova.setIdentificacao(venda.getIdentificacao());
        if(venda.getCliente() != null) {
            Usuario usuario = cadastradorUsuario.cadastrarUsuario(venda.getCliente());
            vendaNova.setCliente(usuario);
        }

        if(venda.getMercadorias() != null) {
            for(Mercadoria mercadoria: venda.getMercadorias()) {
                MercadoriaDTO mercadoriaDTO = new MercadoriaDTO(
                    mercadoria.getNome(),
                    mercadoria.getQuantidade(),
                    mercadoria.getValor(),
                    Optional.ofNullable(mercadoria.getDescricao())
                );
                Mercadoria mercadoriaNova = cadastradorMercadoria.cadastrarMercadoria(mercadoriaDTO);
                repositorioMercadoria.save(mercadoriaNova);
                vendaNova.getMercadorias().add(mercadoriaNova);
            }
        }

        if(venda.getFuncionario() != null) {
            Usuario usuarioFuncionario = cadastradorUsuario.cadastrarUsuario(venda.getFuncionario());
            vendaNova.setFuncionario(usuarioFuncionario);
        }

        if(venda.getServicos() != null) {
            for(Servico servico: venda.getServicos()) {
                vendaNova.getServicos().add(servico);
            }
        }

        if(venda.getVeiculo() != null) {
            vendaNova.setVeiculo(venda.getVeiculo());
        }

        return vendaNova;
    }
}
