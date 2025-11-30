package com.autobots.automanager.modelos.cadastro;

import org.springframework.stereotype.Component;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;

import java.util.Date;

@Component
public class CadastradorUsuario {
    public static Usuario cadastrarUsuario(UsuarioDTO usuario){
        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setNome(usuario.nome());
        usuarioNovo.setNomeSocial(usuario.nomeSocial());
        usuarioNovo.setPerfis(usuario.perfis());

        for (Telefone telefone : usuario.telefones()){
            Telefone telefoneNovo = new Telefone();
            telefoneNovo.setDdd(telefone.getDdd());
            telefoneNovo.setNumero(telefone.getNumero());
            usuarioNovo.getTelefones().add(telefoneNovo);
        }

        Endereco enderecoNovo = new Endereco();
        enderecoNovo.setEstado(usuario.endereco().getEstado());
        enderecoNovo.setCidade(usuario.endereco().getCidade());
        enderecoNovo.setBairro(usuario.endereco().getBairro());
        enderecoNovo.setRua(usuario.endereco().getRua());
        enderecoNovo.setNumero(usuario.endereco().getNumero());
        enderecoNovo.setCodigoPostal(usuario.endereco().getCodigoPostal());
        if (usuario.endereco().getInformacoesAdicionais() !=null){
            enderecoNovo.setInformacoesAdicionais(usuario.endereco().getInformacoesAdicionais());
        }
        usuarioNovo.setEndereco(enderecoNovo);

        for (Documento documento : usuario.documentos()){
            Documento documentoNovo = new Documento();
            documentoNovo.setTipo(documento.getTipo());
            documentoNovo.setNumero(documento.getNumero());
            usuarioNovo.getDocumentos().add(documentoNovo);
        }

        for (Email email : usuario.emails()){
            Email emailNovo = new Email();
            emailNovo.setEndereco(email.getEndereco());
            usuarioNovo.getEmails().add(emailNovo);
        }

        if (usuario.credencial() != null){
            Credencial credencial = new Credencial();
            credencial.setNomeUsuario(usuario.credencial().getNomeUsuario());
            credencial.setSenha(usuario.credencial().getSenha());
            credencial.setInativo(false);
            usuarioNovo.setCredencial(credencial);
        }

        if (usuario.mercadorias() != null) {
            for (MercadoriaDTO mercadoria : usuario.mercadorias()){
                Mercadoria mercadoriaNova = new Mercadoria();
                mercadoriaNova.setNome(mercadoria.nome());
                mercadoriaNova.setValor(mercadoria.valor());
                mercadoriaNova.setQuantidade(mercadoria.quantidade());
                mercadoriaNova.setCadastro(new Date());
                mercadoriaNova.setFabricao(new Date());
                mercadoriaNova.setValidade(new Date());
                mercadoriaNova.setDescricao(mercadoria.descricao().orElse(null));
                usuarioNovo.getMercadorias().add(mercadoriaNova);
            }
        }

        if (usuario.vendas() != null){
            usuarioNovo.setVendas(usuario.vendas());
        }

        if (usuario.veiculos() != null){
            usuarioNovo.setVeiculos(usuario.veiculos());
        }
        return usuarioNovo;
    }

    public Usuario cadastrarUsuario(Usuario usuario){
        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setNome(usuario.getNome());
        usuarioNovo.setNomeSocial(usuario.getNomeSocial());
        usuarioNovo.setPerfis(usuario.getPerfis());

        for (Telefone telefone : usuario.getTelefones()){
            Telefone telefoneNovo = new Telefone();
            telefoneNovo.setDdd(telefone.getDdd());
            telefoneNovo.setNumero(telefone.getNumero());
            usuarioNovo.getTelefones().add(telefoneNovo);
        }

        Endereco enderecoNovo = new Endereco();
        enderecoNovo.setEstado(usuario.getEndereco().getEstado());
        enderecoNovo.setCidade(usuario.getEndereco().getCidade());
        enderecoNovo.setBairro(usuario.getEndereco().getBairro());
        enderecoNovo.setRua(usuario.getEndereco().getRua());
        enderecoNovo.setNumero(usuario.getEndereco().getNumero());
        enderecoNovo.setCodigoPostal(usuario.getEndereco().getCodigoPostal());
        if (usuario.getEndereco().getInformacoesAdicionais() != null){
            enderecoNovo.setInformacoesAdicionais(usuario.getEndereco().getInformacoesAdicionais());
        }
        usuarioNovo.setEndereco(enderecoNovo);

        for (Documento documento : usuario.getDocumentos()){
            Documento documentoNovo = new Documento();
            documentoNovo.setTipo(documento.getTipo());
            documentoNovo.setNumero(documento.getNumero());
            documentoNovo.setDataEmissao(new Date());
            usuarioNovo.getDocumentos().add(documentoNovo);
        }

        for (Email email : usuario.getEmails()){
            Email emailNovo = new Email();
            emailNovo.setEndereco(email.getEndereco());
            usuarioNovo.getEmails().add(emailNovo);
        }

        if (usuario.getMercadorias() != null){
            for (Mercadoria mercadoria : usuario.getMercadorias()){
                Mercadoria mercadoriaNova = new Mercadoria();
                mercadoriaNova.setNome(mercadoria.getNome());
                mercadoriaNova.setValor(mercadoria.getValor());
                mercadoriaNova.setQuantidade(mercadoria.getQuantidade());
                mercadoriaNova.setCadastro(new Date());
                mercadoriaNova.setFabricao(new Date());
                mercadoriaNova.setValidade(new Date());
                mercadoriaNova.setDescricao(mercadoria.getDescricao());
                usuarioNovo.getMercadorias().add(mercadoriaNova);
            }
        }

        if (usuario.getVendas() != null){
            usuarioNovo.setVendas(usuario.getVendas());
        }

        if (usuario.getVeiculos() != null){
            usuarioNovo.setVeiculos(usuario.getVeiculos());
        }

        return usuarioNovo;
    }
}