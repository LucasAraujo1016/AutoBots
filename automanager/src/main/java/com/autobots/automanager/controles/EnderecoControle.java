package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.cliente.ClienteSelecionador;
import com.autobots.automanager.modelo.endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.endereco.EnderecoCadastrar;
import com.autobots.automanager.modelo.endereco.EnderecoRemovedor;
import com.autobots.automanager.modelo.endereco.EnderecoVisualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio repositorio;

    @Autowired
    private ClienteSelecionador selecionador;

    @Autowired
    private EnderecoCadastrar cadastrar;

    @Autowired
    private EnderecoRemovedor removedor;

    @Autowired
    private EnderecoAtualizador atualizador;

    @Autowired
    private EnderecoVisualizador visualizador;

    @PostMapping("/cadastro/{id}")
    public void cadastrarEndereco(@RequestBody List<Endereco> enderecos, @PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        cadastrar.cadastro(cliente, enderecos);
        repositorio.save(cliente);
    }

    @GetMapping("/visualizar/{idCliente}")
    public ResponseEntity<Endereco> visualizarEndereco(@RequestBody Endereco enderecoBusca, @PathVariable long idCliente) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, idCliente);
        Endereco endereco = visualizador.visualizar(List.of(cliente.getEndereco()), enderecoBusca);
        if (endereco != null) {
            return ResponseEntity.ok(endereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar/{idCliente}")
    public List<Endereco> listarEnderecos(@PathVariable long idCliente) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, idCliente);
        return cliente.getEndereco() != null ? List.of(cliente.getEndereco()) : List.of();
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarEndereco(@RequestBody Endereco endereco,@PathVariable Long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        atualizador.atualizar(cliente.getEndereco(), endereco);
        repositorio.save(cliente);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirEndereco(@RequestBody List<Endereco> enderecos, @PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        removedor.remover(cliente, enderecos);
        repositorio.save(cliente);
    }
}
