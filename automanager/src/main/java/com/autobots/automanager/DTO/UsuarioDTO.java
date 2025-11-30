package com.autobots.automanager.DTO;

import java.util.Set;

import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Email;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public record UsuarioDTO(
    String nome,
    String nomeSocial,
    Set<PerfilUsuario> perfis,
    Set<Telefone> telefones,
    Endereco endereco,
    Set<Documento> documentos,
    Set<Email> emails,
    Set<CredencialUsuarioSenha> credenciais,
    Set<Mercadoria> mercadorias,
    Set<Venda> vendas,
    Set<Veiculo> veiculos
) {
} 

