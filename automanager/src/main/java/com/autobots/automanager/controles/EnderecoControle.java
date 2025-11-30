package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.cliente.ClienteSelecionador;
import com.autobots.automanager.modelos.endereco.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.endereco.EnderecoAtualizador;
import com.autobots.automanager.modelos.endereco.EnderecoCadastrar;
import com.autobots.automanager.modelos.endereco.EnderecoRemovedor;
import com.autobots.automanager.modelos.endereco.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteSelecionador clienteSelecionador;

    @Autowired
    private AdicionadorLinkEndereco adicionadorLinkEndereco;

    @Autowired
    private EnderecoAtualizador enderecoAtualizador;

    @Autowired
    private EnderecoSelecionador enderecoSelecionador;

    @Autowired
    private EnderecoCadastrar enderecoCadastrar;

    @Autowired
    private EnderecoRemovedor enderecoRemovedor;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Endereco> visualizarEndereco(@PathVariable long id) {
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        Endereco endereco = enderecoSelecionador.selecionar(enderecos, id);
        if (endereco == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEndereco.adicionarLink(endereco);
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Endereco>> listaEnderecos(){
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            adicionadorLinkEndereco.adicionarLink(enderecos);
            return new ResponseEntity<>(enderecos, HttpStatus.OK);
        }
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<Endereco> visualizarEnderecoCliente(@PathVariable long clienteid) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null || cliente.getEndereco() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkEndereco.adicionarLink(cliente.getEndereco());
            return new ResponseEntity<>(cliente.getEndereco(), HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar/{clienteid}")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco, @PathVariable long clienteid) {
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente != null) {
            enderecoCadastrar.cadastro(cliente, endereco);
            clienteRepositorio.save(cliente);
            status = HttpStatus.CREATED;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco endereco, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null || cliente.getEndereco() == null){
            status = HttpStatus.BAD_REQUEST;
        } else {
            enderecoAtualizador.atualizar(cliente.getEndereco(), endereco);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/remover/{clienteid}")
    public ResponseEntity<?> removerEndereco(@PathVariable long clienteid) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente != null && cliente.getEndereco() != null) {
            enderecoRemovedor.remover(cliente, cliente.getEndereco());
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}