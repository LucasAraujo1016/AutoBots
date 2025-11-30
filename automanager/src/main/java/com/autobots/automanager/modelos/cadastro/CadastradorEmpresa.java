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
                Telefone telefoneNovo = new Telefone();
                telefoneNovo.setDdd(telefone.getDdd());
                telefoneNovo.setNumero(telefone.getNumero());
                empresaNova.getTelefones().add(telefoneNovo);
            }
        }

        if (empresa.getEndereco() != null) {
            Endereco enderecoNovo = new Endereco();
            enderecoNovo.setEstado(empresa.getEndereco().getEstado());
            enderecoNovo.setCidade(empresa.getEndereco().getCidade());
            enderecoNovo.setBairro(empresa.getEndereco().getBairro());
            enderecoNovo.setRua(empresa.getEndereco().getRua());
            enderecoNovo.setNumero(empresa.getEndereco().getNumero());
            enderecoNovo.setCodigoPostal(empresa.getEndereco().getCodigoPostal());
            if (empresa.getEndereco().getInformacoesAdicionais() != null) {
                enderecoNovo.setInformacoesAdicionais(empresa.getEndereco().getInformacoesAdicionais());
            }
            empresaNova.setEndereco(enderecoNovo);
        }

        empresaNova.setCadastro(new Date());

        if (empresa.getServicos() != null){
            for (Servico servico : empresa.getServicos()){
                empresaNova.getServicos().add(servico);
            }
        }

        if (empresa.getUsuarios() != null){
            for (Usuario usuario : empresa.getUsuarios()){
                Usuario usuarioNovo = cadastradorUsuario.cadastrarUsuario(usuario);
                empresaNova.getUsuarios().add(usuarioNovo);
            }
        }

        if (empresa.getVendas() != null){
            for (Venda venda : empresa.getVendas()){
                Venda vendaNova = cadastradorVenda.cadastrarVenda(venda);
                empresaNova.getVendas().add(vendaNova);
            }
        }

        if (empresa.getMercadorias() != null){
            for (Mercadoria mercadoria : empresa.getMercadorias()){
                Mercadoria mercadoriaNova = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
                empresaNova.getMercadorias().add(mercadoriaNova);
            }
        }

        return empresaNova;
    }
}