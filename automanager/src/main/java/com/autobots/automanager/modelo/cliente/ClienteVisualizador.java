package com.autobots.automanager.modelo.cliente;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.StringVerificadorNulo;

@Component
public class ClienteVisualizador {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public Cliente visualizar(List<Cliente> clientes, Cliente clienteBusca) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() != null && clienteBusca.getId() != null) {
                if (cliente.getId().equals(clienteBusca.getId())) {
                    return cliente;
                }
            } else if (!verificadorNulo.verificar(cliente.getNome()) &&
                       cliente.getNome().equals(clienteBusca.getNome())) {
                return cliente;
            }
        }
        return null;
    }
}
