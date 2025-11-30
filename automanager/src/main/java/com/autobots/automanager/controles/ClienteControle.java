package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.cliente.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.cliente.ClienteAtualizador;
import com.autobots.automanager.modelos.cliente.ClienteSelecionador;
import com.autobots.automanager.modelos.cliente.ClienteCadastrador;
import com.autobots.automanager.modelos.cliente.ClienteRemovedor;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class ClienteControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private AdicionadorLinkCliente adicionadorLink;
    @Autowired
    private ClienteCadastrador cadastrador;
    @Autowired
    private ClienteAtualizador atualizador;
    @Autowired
    private ClienteRemovedor removedor;

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK); // Corrigido de FOUND para OK
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> obterClientes() {
        List<Cliente> clientes = repositorio.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLink.adicionarLink(clientes);
            return new ResponseEntity<>(clientes, HttpStatus.OK); 
        }
    }

    @PostMapping("/cliente/cadastro")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = repositorio.findAll();
        if (cliente.getId() == null) {
            cadastrador.cadastro(clientes, cliente);
            repositorio.save(cliente);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/cliente/atualizar")
    public ResponseEntity<?> atualizarCliente(@RequestBody Cliente atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Cliente cliente = repositorio.findById(atualizacao.getId()).orElse(null); 
        if (cliente != null) {
            atualizador.atualizar(cliente, atualizacao);
            repositorio.save(cliente);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/cliente/excluir")
    public ResponseEntity<?> excluirCliente(@RequestBody Cliente exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Cliente cliente = repositorio.findById(exclusao.getId()).orElse(null); // corrigido
        if (cliente != null) {
            List<Cliente> clientes = repositorio.findAll();
            removedor.remover(clientes, cliente);
            repositorio.delete(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}
