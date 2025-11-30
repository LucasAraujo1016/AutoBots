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
            Telefone setTelefone = new Telefone();
            setTelefone.setDdd(telefone.getDdd());
            setTelefone.setNumero(telefone.getNumero());
            usuarioNovo.getTelefones().add(setTelefone);
        }

        Endereco setEndereco = new Endereco();
        setEndereco.setEstado(usuario.endereco().getEstado());
        setEndereco.setCidade(usuario.endereco().getCidade());
        setEndereco.setBairro(usuario.endereco().getBairro());
        setEndereco.setRua(usuario.endereco().getRua());
        setEndereco.setNumero(usuario.endereco().getNumero());
        setEndereco.setCodigoPostal(usuario.endereco().getCodigoPostal());
        if (usuario.endereco().getInformacoesAdicionais() !=null){
            setEndereco.setInformacoesAdicionais(usuario.endereco().getInformacoesAdicionais());
        }
        usuarioNovo.setEndereco(setEndereco); // setEndereco de dentro do parenteses é o objeto criado acima, ficou confuso eu sei :(

        for (Documento documento : usuario.documentos()){
            Documento setDocumento = new Documento();
            setDocumento.setTipo(documento.getTipo());
            setDocumento.setNumero(documento.getNumero());
            usuarioNovo.getDocumentos().add(setDocumento);
        }

        for (Email email : usuario.emails()){
            Email setEmail = new Email();
            setEmail.setEndereco(email.getEndereco());
            usuarioNovo.getEmails().add(setEmail);
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
                Mercadoria setMercadoria = new Mercadoria();
                setMercadoria.setNome(mercadoria.nome());
                setMercadoria.setValor(mercadoria.valor());
                setMercadoria.setQuantidade(mercadoria.quantidade());
                setMercadoria.setCadastro(new Date());
                setMercadoria.setFabricao(new Date());
                setMercadoria.setValidade(new Date());
                setMercadoria.setDescricao(mercadoria.descricao().orElse(null));
                usuarioNovo.getMercadorias().add(setMercadoria);
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
        Usuario setUsario = new Usuario();
        setUsario.setNome(usuario.getNome());
        setUsario.setNomeSocial(usuario.getNomeSocial());
        setUsario.setPerfis(usuario.getPerfis());

        for (Telefone telefone : usuario.getTelefones()){
            Telefone setTelefone = new Telefone();
            setTelefone.setDdd(telefone.getDdd());
            setTelefone.setNumero(telefone.getNumero());
            setUsario.getTelefones().add(setTelefone);
        }

        Endereco setEndereco = new Endereco();
        setEndereco.setEstado(usuario.getEndereco().getEstado());
        setEndereco.setCidade(usuario.getEndereco().getCidade());
        setEndereco.setBairro(usuario.getEndereco().getBairro());
        setEndereco.setRua(usuario.getEndereco().getRua());
        setEndereco.setNumero(usuario.getEndereco().getNumero());
        setEndereco.setCodigoPostal(usuario.getEndereco().getCodigoPostal());
        if (usuario.getEndereco().getInformacoesAdicionais() != null){
            setEndereco.setInformacoesAdicionais(usuario.getEndereco().getInformacoesAdicionais());
        }
        setUsario.setEndereco(setEndereco);

        for (Documento documento : usuario.getDocumentos()){
            Documento setDocumento = new Documento();
            setDocumento.setTipo(documento.getTipo());
            setDocumento.setNumero(documento.getNumero());
            setDocumento.setDataEmissao(new Date());
            setUsario.getDocumentos().add(setDocumento);
        }

        for (Email email : usuario.getEmails()){
            Email setEmail = new Email();
            setEmail.setEndereco(email.getEndereco());
            setUsario.getEmails().add(setEmail);
        }

        if (usuario.getMercadorias() != null){
            for (Mercadoria mercadoria : usuario.getMercadorias()){
                Mercadoria setMercadoria = new Mercadoria();
                setMercadoria.setNome(mercadoria.getNome());
                setMercadoria.setValor(mercadoria.getValor());
                setMercadoria.setQuantidade(mercadoria.getQuantidade());
                setMercadoria.setCadastro(new Date());
                setMercadoria.setFabricao(new Date());
                setMercadoria.setValidade(new Date());
                setMercadoria.setDescricao(mercadoria.getDescricao());
                setUsario.getMercadorias().add(setMercadoria);
            }
        }

        if (usuario.getVendas() != null){
            setUsario.setVendas(usuario.getVendas());
        }

        if (usuario.getVeiculos() != null){
            setUsario.setVeiculos(usuario.getVeiculos());
        }

        return setUsario;
    }
}