package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.ServicoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {

    @Autowired
    private ServicoServico servicoServico;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico, @RequestParam Usuario usuario) {
        try {
            servicoServico.cadastrarServico(servico, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarServicoEmpresa(@RequestBody Servico servico, @PathVariable Long idEmpresa) {
        try {
            servicoServico.cadastrarServicoEmpresa(servico, idEmpresa);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/venda/{idVenda}")
    public ResponseEntity<?> cadastrarServicoVenda(@RequestBody Servico servico, @PathVariable Long idVenda) {
        try {
            servicoServico.cadastrarServicoVenda(servico, idVenda);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id, @RequestParam Usuario usuario) {
        try {
            servicoServico.deletarServico(id, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Long id, @RequestBody Servico servico, @RequestParam Usuario usuario) {
        try {
            servicoServico.atualizarServico(id, servico, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Servico>> listarServicos(@RequestParam Usuario usuario) {
        List<Servico> servicos = servicoServico.listarServicos(usuario);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Servico> visualizarServico(@PathVariable Long id, @RequestParam Usuario usuario) {
        Servico servico = servicoServico.visualizarServico(id, usuario);
        if (servico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servico);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/venda/{idVenda}")
    public ResponseEntity<List<Servico>> visualizarServicosVenda(@PathVariable Long idVenda) {
        List<Servico> servicos = servicoServico.visualizarServicosVenda(idVenda);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Servico>> visualizarServicosEmpresa(@PathVariable Long idEmpresa) {
        List<Servico> servicos = servicoServico.visualizarServicosEmpresa(idEmpresa);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idServico}/venda/{idVenda}")
    public ResponseEntity<?> vincularServicoVenda(@PathVariable Long idServico, @PathVariable Long idVenda) {
        try {
            servicoServico.vincularServicoVenda(idServico, idVenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idServico}/venda/{idVenda}")
    public ResponseEntity<?> desvincularServicoVenda(@PathVariable Long idServico, @PathVariable Long idVenda) {
        try {
            servicoServico.desvincularServicoVenda(idServico, idVenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idServico}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularServicoEmpresa(@PathVariable Long idServico, @PathVariable Long idEmpresa) {
        try {
            servicoServico.vincularServicoEmpresa(idServico, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idServico}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularServicoEmpresa(@PathVariable Long idServico, @PathVariable Long idEmpresa) {
        try {
            servicoServico.desvincularServicoEmpresa(idServico, idEmpresa);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}