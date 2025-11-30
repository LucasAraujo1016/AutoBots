package com.autobots.automanager.DTO;

import java.util.Optional;
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

public record AtualizarUsuarioDTO(
    Optional<String> nome,
    Optional<String> nomeSocial,
    Optional<Set<PerfilUsuario>> perfis,
    Optional<Set<Telefone>> telefones,
    Optional<Endereco> endereco,
    Optional<Set<Documento>> documentos,
    Optional<Set<Email>> emails,
    Optional<Set<CredencialUsuarioSenha>> credenciais,
    Optional<Set<Mercadoria>> mercadorias,
    Optional<Set<Venda>> vendas,
    Optional<Set<Veiculo>> veiculos
) {
}
