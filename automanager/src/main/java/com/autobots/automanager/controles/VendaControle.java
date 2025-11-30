package com.autobots.automanager.controles;

import com.autobots.automanager.DTO.AtualizarVendaDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.servicos.VendaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaControle {

    @Autowired
    private VendaServico vendaServico;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        try {
            vendaServico.cadastrarVenda(venda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarVendaEmpresa(@PathVariable Long idEmpresa, @RequestBody Venda venda) {
        try {
            vendaServico.cadastrarVendaEmpresa(idEmpresa, venda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> cadastrarVendaUsuario(@PathVariable Long idUsuario, @RequestBody Venda venda, @PathVariable String tipoUsuario) {
        try {
            vendaServico.cadastrarVendaUsuario(idUsuario, venda, tipoUsuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        try {
            vendaServico.deletarVenda(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody AtualizarVendaDTO venda) {
        try {
            vendaServico.atualizarVenda(id, venda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaServico.listarVendas();
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Venda> visualizarVenda(@PathVariable Long id, @RequestParam Usuario usuario) {
        Venda venda = vendaServico.visualizarVenda(id, usuario);
        if (venda == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venda);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Venda>> listarVendasEmpresa(@PathVariable Long idEmpresa) {
        List<Venda> vendas = vendaServico.visualizarVendasEmpresa(idEmpresa);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<List<Venda>> listarVendasUsuario(@PathVariable Long idUsuario) {
        List<Venda> vendas = vendaServico.visualizarVendasUsuario(idUsuario);
        if (vendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendas);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> vincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaServico.vincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaServico.vincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idVenda}/usuario/{idUsuario}/{tipoUsuario}")
    public ResponseEntity<?> desvincularVendaUsuario(@PathVariable Long idVenda, @PathVariable Long idUsuario, @PathVariable String tipoUsuario) {
        try {
            vendaServico.desvincularVendaUsuario(idVenda, idUsuario, tipoUsuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idVenda}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularVendaEmpresa(@PathVariable Long idVenda, @PathVariable Long idEmpresa) {
        try {
            vendaServico.desvincularVendaEmpresa(idVenda, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}