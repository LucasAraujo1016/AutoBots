package com.autobots.automanager.modelo.endereco;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.StringVerificadorNulo;

@Component
public class EnderecoRemovedor {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public void remover(Cliente cliente, Endereco endereco) {
        if (endereco != null && cliente.getEndereco() != null) {
            if ((endereco.getId() != null && endereco.getId().equals(cliente.getEndereco().getId())) ||
                (!verificadorNulo.verificar(endereco.getCidade()) &&
                 !verificadorNulo.verificar(endereco.getRua()) &&
                 !verificadorNulo.verificar(endereco.getNumero()) &&
                 endereco.getCidade().equals(cliente.getEndereco().getCidade()) &&
                 endereco.getRua().equals(cliente.getEndereco().getRua()) &&
                 endereco.getNumero().equals(cliente.getEndereco().getNumero()))) {
                cliente.setEndereco(null);
            }
        }
    }

    public void remover(Cliente cliente, List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            remover(cliente, endereco);
        }
    }
}
