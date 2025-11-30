package com.autobots.automanager.modelos.cliente;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Component
public class ClienteRemovedor {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public void remover(List<Cliente> clientes, Cliente clienteRemover) {
        clientes.removeIf(cliente -> {
            if (clienteRemover.getId() != null && cliente.getId() != null) {
                return clienteRemover.getId().equals(cliente.getId());
            } else if (!verificadorNulo.verificar(clienteRemover.getNome()) &&
                       clienteRemover.getNome().equals(cliente.getNome())) {
                return true;
            }
            return false;
        });
    }

    public void remover(List<Cliente> clientes, List<Cliente> clientesRemover) {
        for (Cliente cliente : clientesRemover) {
            remover(clientes, cliente);
        }
    }
}
