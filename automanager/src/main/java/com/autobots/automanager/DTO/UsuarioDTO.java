package com.autobots.automanager.DTO;



import java.util.Set;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.Perfil;

public record UsuarioDTO(
        String nome,
        String nomeSocial,
        Set<Perfil> perfis,
        Set<Telefone> telefones,
        Endereco endereco,
        Set<Documento> documentos,
        Set<Email> emails,
        Credencial credencial,
        Set<MercadoriaDTO> mercadorias,
        Set<Venda> vendas,
        Set<Veiculo> veiculos
) {
}