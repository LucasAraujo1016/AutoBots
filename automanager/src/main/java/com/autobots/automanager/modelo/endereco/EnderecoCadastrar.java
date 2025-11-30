package com.autobots.automanager.modelo.endereco;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.StringVerificadorNulo;

@Component
public class EnderecoCadastrar {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public void cadastro(Cliente cliente, Endereco endereco) {
        if (endereco != null) {
            if (!verificadorNulo.verificar(endereco.getCidade()) &&
                !verificadorNulo.verificar(endereco.getRua()) &&
                !verificadorNulo.verificar(endereco.getNumero())) {
                Endereco novoEndereco = new Endereco();
                novoEndereco.setEstado(endereco.getEstado());
                novoEndereco.setCidade(endereco.getCidade());
                novoEndereco.setBairro(endereco.getBairro());
                novoEndereco.setRua(endereco.getRua());
                novoEndereco.setNumero(endereco.getNumero());
                novoEndereco.setCodigoPostal(endereco.getCodigoPostal());
                novoEndereco.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
                cliente.setEndereco(novoEndereco);
            }
        }
    }

    public void cadastro(Cliente cliente, List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            cadastro(cliente, endereco);
        }
    }
}
