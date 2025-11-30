package com.autobots.automanager.modelos.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;

import java.util.Date;

@Component
public class CadastradorEmpresa {

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private CadastradorUsuario cadastradorUsuario;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    public Empresa cadastrarEmpresa(Empresa empresa){
        Empresa empresaNova = new Empresa();
        empresaNova.setNomeFantasia(empresa.getNomeFantasia());
        empresaNova.setRazaoSocial(empresa.getRazaoSocial());
        if (empresa.getTelefones() != null){
            for (Telefone telefone : empresa.getTelefones()){
                Telefone setTelefone = new Telefone();
                setTelefone.setDdd(telefone.getDdd());
                setTelefone.setNumero(telefone.getNumero());
                empresaNova.getTelefones().add(setTelefone);
            }
        }

        if (empresa.getEndereco() != null) {
            Endereco setEndereco = new Endereco();
            setEndereco.setEstado(empresa.getEndereco().getEstado());
            setEndereco.setCidade(empresa.getEndereco().getCidade());
            setEndereco.setBairro(empresa.getEndereco().getBairro());
            setEndereco.setRua(empresa.getEndereco().getRua());
            setEndereco.setNumero(empresa.getEndereco().getNumero());
            setEndereco.setCodigoPostal(empresa.getEndereco().getCodigoPostal());
            if (empresa.getEndereco().getInformacoesAdicionais() != null) {
                setEndereco.setInformacoesAdicionais(empresa.getEndereco().getInformacoesAdicionais());
            }
            empresaNova.setEndereco(setEndereco);
        }

        empresaNova.setCadastro(new Date());

        if (empresa.getServicos() != null){
            for (Servico servico : empresa.getServicos()){
                empresaNova.getServicos().add(servico);
            }
        }

        if (empresa.getUsuarios() != null){
            for (Usuario usuario : empresa.getUsuarios()){
                Usuario setUsuario = cadastradorUsuario.cadastrarUsuario(usuario);
                empresaNova.getUsuarios().add(setUsuario);
            }
        }

        if (empresa.getVendas() != null){
            for (Venda venda : empresa.getVendas()){
                Venda setVenda = cadastradorVenda.cadastrarVenda(venda);
                empresaNova.getVendas().add(setVenda);
            }
        }

        if (empresa.getMercadorias() != null){
            for (Mercadoria mercadoria : empresa.getMercadorias()){
                Mercadoria setMercadoria = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
                empresaNova.getMercadorias().add(setMercadoria);
            }
        }

        return empresaNova;
    }
}