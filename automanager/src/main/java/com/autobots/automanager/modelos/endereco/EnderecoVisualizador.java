package com.autobots.automanager.modelos.endereco;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Component
public class EnderecoVisualizador {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public Endereco visualizar(List<Endereco> enderecos, Endereco enderecoBusca) {
        for (Endereco endereco : enderecos) {
            if (endereco.getId() != null && enderecoBusca.getId() != null) {
                if (endereco.getId().equals(enderecoBusca.getId())) {
                    return endereco;
                }
            } else if (!verificadorNulo.verificar(endereco.getCidade()) &&
                       !verificadorNulo.verificar(endereco.getRua()) &&
                       !verificadorNulo.verificar(endereco.getNumero()) &&
                       endereco.getCidade().equals(enderecoBusca.getCidade()) &&
                       endereco.getRua().equals(enderecoBusca.getRua()) &&
                       endereco.getNumero().equals(enderecoBusca.getNumero())) {
                return endereco;
            }
        }
        return null;
    }
}
