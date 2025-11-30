package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.telefone.TelefoneCadastrar;
import com.autobots.automanager.modelo.telefone.TelefoneRemovedor;
import com.autobots.automanager.modelo.telefone.TelefoneVisualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @Autowired
    private ClienteSelecionador selecionador;

    @Autowired
    private TelefoneCadastrar cadastrar;

    @Autowired
    private TelefoneRemovedor removedor;

    @Autowired
    private TelefoneAtualizador atualizador;

    @Autowired
    private TelefoneVisualizador visualizador;

    @PostMapping("/cadastro/{id}")
    public void cadastroTelefone(@RequestBody List<Telefone> telefones, @PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        cadastrar.cadastro(cliente, telefones);
        repositorio.save(cliente);
    }

    @GetMapping("/visualizar/{idCliente}")
    public ResponseEntity<Telefone> visualizarTelefone(@RequestBody Telefone telefoneBusca, @PathVariable long idCliente) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, idCliente);
        Telefone telefone = visualizador.visualizar(cliente.getTelefones(), telefoneBusca);
        if (telefone != null) {
            return ResponseEntity.ok(telefone);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarTelefone(@RequestBody List<Telefone> telefones, @PathVariable long id){
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        atualizador.atualizar(cliente.getTelefones(), telefones);
        repositorio.save(cliente);
    }

    @DeleteMapping("/excluir")
    public void excluirTelefone(@RequestBody List<Telefone> telefones, @PathVariable long id){
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        removedor.remover(cliente, telefones);
        repositorio.save(cliente);
    }
}