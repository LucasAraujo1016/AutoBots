package com.autobots.automanager.modelos.cliente;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;

@Component
public class ClienteCadastrador {

    public void cadastro(List<Cliente> clientes, Cliente cliente) {
        if (cliente != null) {
            Cliente novoCliente = new Cliente();
            if (cliente.getNome() != null) {
                novoCliente.setNome(cliente.getNome());
            }
            if (cliente.getNomeSocial() != null) {
                novoCliente.setNomeSocial(cliente.getNomeSocial());
            }
            if (cliente.getDataNascimento() != null) {
                novoCliente.setDataNascimento(cliente.getDataNascimento());
            }
            if (cliente.getDataCadastro() != null) {
                novoCliente.setDataCadastro(cliente.getDataCadastro());
            }
            if (cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
                novoCliente.setDocumentos(cliente.getDocumentos());
            }
            if (cliente.getEndereco() != null) {
                novoCliente.setEndereco(cliente.getEndereco());
            }
            if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
                novoCliente.setTelefones(cliente.getTelefones());
            }
            clientes.add(novoCliente);
        }
    }

    public void cadastro(List<Cliente> clientes, List<Cliente> novosClientes) {
        for (Cliente cliente : novosClientes) {
            cadastro(clientes, cliente);
        }
    }
}
