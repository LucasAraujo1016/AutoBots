package com.autobots.automanager.DTO;


import java.util.Optional;
import java.util.Set;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.Perfil;

public record AtualizarUsuarioDTO(
        Optional<String> nome,
        Optional<String> nomeSocial,
        Optional<Set<Perfil>> perfis,
        Optional<Set<Telefone>> telefones,
        Optional<Endereco> endereco,
        Optional<Set<Documento>> documentos,
        Optional<Set<Email>> emails,
        Optional<Credencial> credencial,
        Optional<Set<Mercadoria>> mercadorias,
        Optional<Set<Venda>> vendas,
        Optional<Set<Veiculo>> veiculos
){
}