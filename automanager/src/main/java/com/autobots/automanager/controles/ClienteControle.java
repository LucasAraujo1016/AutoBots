package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.cliente.ClienteAtualizador;
import com.autobots.automanager.modelo.cliente.ClienteCadastrador;
import com.autobots.automanager.modelo.cliente.ClienteRemovedor;
import com.autobots.automanager.modelo.cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.cliente.ClienteVisualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @Autowired
    private ClienteSelecionador selecionador;

    @Autowired
    private ClienteCadastrador cadastrador;

    @Autowired
    private ClienteAtualizador atualizador;

    @Autowired
    private ClienteRemovedor removedor;

    @Autowired
    private ClienteVisualizador visualizador;

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Cliente> visualizarCliente(@RequestBody Cliente clienteBusca, @PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        Cliente resultado = visualizador.visualizar(List.of(cliente), clienteBusca);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return repositorio.findAll();
    }

    @PostMapping("/cadastro")
    public void cadastrarCliente(@RequestBody Cliente cliente) {
        List<Cliente> clientes = repositorio.findAll();
        cadastrador.cadastro(clientes, cliente);
        for (Cliente novoCliente : clientes) {
            if (novoCliente.getId() == null) { 
                repositorio.save(novoCliente);
            }
        }
    }

    @PutMapping("/atualizar")
    public void atualizarCliente(@RequestBody Cliente atualizacao) {
        Cliente cliente = repositorio.getById(atualizacao.getId());
        atualizador.atualizar(cliente, atualizacao);
        repositorio.save(cliente);
    }

    @DeleteMapping("/excluir")
    public void excluirCliente(@RequestBody Cliente exclusao) {
        List<Cliente> clientes = repositorio.findAll();
        removedor.remover(clientes, exclusao);
        repositorio.deleteById(exclusao.getId());
    }
}
