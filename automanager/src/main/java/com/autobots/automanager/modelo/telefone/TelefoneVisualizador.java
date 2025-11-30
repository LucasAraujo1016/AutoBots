package com.autobots.automanager.modelo.telefone;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.StringVerificadorNulo;

@Component
public class TelefoneVisualizador {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public Telefone visualizar(List<Telefone> telefones, Telefone telefoneBusca) {
        for (Telefone telefone : telefones) {
            if (telefone.getId() != null && telefoneBusca.getId() != null) {
                if (telefone.getId().equals(telefoneBusca.getId())) {
                    return telefone;
                }
            } else if (!verificadorNulo.verificar(telefone.getDdd()) &&
                       !verificadorNulo.verificar(telefone.getNumero()) &&
                       telefone.getDdd().equals(telefoneBusca.getDdd()) &&
                       telefone.getNumero().equals(telefoneBusca.getNumero())) {
                return telefone;
            }
        }
        return null;
    }
}
