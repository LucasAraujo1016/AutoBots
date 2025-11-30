package com.autobots.automanager.modelos.cadastro;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class CadastradorVenda {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    public Venda cadastrarVenda(Venda venda){
        Venda vendaNova = new Venda();
        vendaNova.setCadastro(new Date());
        vendaNova.setIdentificacao(venda.getIdentificacao());
        if (venda.getCliente() != null){
            Usuario usuario = cadastradorUsuario.cadastrarUsuario(venda.getCliente());
            vendaNova.setCliente(usuario);
        }
        if (venda.getMercadorias() != null){
            for (Mercadoria mercadoria : venda.getMercadorias()){
                MercadoriaDTO mercadoriaDto = new MercadoriaDTO(
                        mercadoria.getNome(),
                        mercadoria.getQuantidade(),
                        mercadoria.getValor(),
                        Optional.ofNullable(mercadoria.getDescricao())
                );
                Mercadoria novaMercadoria = cadastradorMercadoria.cadastrarMercadoria(mercadoriaDto);
                repositorioMercadoria.save(novaMercadoria);
                vendaNova.getMercadorias().add(novaMercadoria);
            }
        }

        if (venda.getFuncionario() != null){
            Usuario usuarioFuncionario = cadastradorUsuario.cadastrarUsuario(venda.getFuncionario());
            vendaNova.setFuncionario(usuarioFuncionario);
        }

        if (venda.getServicos() != null){
            for (Servico servico : venda.getServicos()) {
                vendaNova.getServicos().add(servico);
            }
        }

        if (venda.getVeiculo() != null){
            vendaNova.setVeiculo(venda.getVeiculo());
        }

        return vendaNova;
    }
}