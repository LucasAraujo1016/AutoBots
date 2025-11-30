package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.MercadoriaServico;

import java.util.List;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {

    @Autowired
    private MercadoriaServico mercadoriaServico;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody MercadoriaDTO mercadoria, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.cadastrarMercadoria(mercadoria, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarMercadoriaEmpresa(@RequestBody MercadoriaDTO mercadoria, @PathVariable Long idEmpresa, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.cadastrarMercadoriaEmpresa(mercadoria, idEmpresa, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar/usuario/{idUsuario}")
    public ResponseEntity<?> cadastrarMercadoriaUsuario(@RequestBody MercadoriaDTO mercadoria, @PathVariable Long idUsuario, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.cadastrarMercadoriaUsuario(mercadoria, idUsuario, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMercadoria(@PathVariable Long id, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.deletarMercadoria(id, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.atualizarMercadoria(id, mercadoria, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Mercadoria>> listarMercadorias(@RequestParam Usuario usuario) {
        List<Mercadoria> mercadorias = mercadoriaServico.listarMercadorias(usuario);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Mercadoria> visualizarMercadoria(@PathVariable Long id, @RequestParam Usuario usuario) {
        Mercadoria mercadoria = mercadoriaServico.visualizarMercadoria(id, usuario);
        if (mercadoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mercadoria);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/empresa/{idEmpresa}")
    public ResponseEntity<List<Mercadoria>> visualizarMercadoriaEmpresa(@PathVariable Long idEmpresa, @RequestParam Usuario usuario) {
        List<Mercadoria> mercadorias = mercadoriaServico.visualizarMercadoriaEmpresa(idEmpresa, usuario);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @GetMapping("/visualizar/usuario/{idUsuario}")
    public ResponseEntity<List<Mercadoria>> visualizarMercadoriaUsuario(@PathVariable Long idUsuario, @RequestParam Usuario usuario) {
        List<Mercadoria> mercadorias = mercadoriaServico.visualizarMercadoriaUsuario(idUsuario, usuario);
        if (mercadorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mercadorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.vincularMercadoriaEmpresa(idMercadoria, idEmpresa, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/vincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> vincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.vincularMercadoriaUsuario(idMercadoria, idUsuario, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idMercadoria}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularMercadoriaEmpresa(@PathVariable Long idMercadoria, @PathVariable Long idEmpresa, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.desvincularMercadoriaEmpresa(idMercadoria, idEmpresa, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/desvincular/{idMercadoria}/usuario/{idUsuario}")
    public ResponseEntity<?> desvincularMercadoriaUsuario(@PathVariable Long idMercadoria, @PathVariable Long idUsuario, @RequestParam Usuario usuario) {
        try {
            mercadoriaServico.desvincularMercadoriaUsuario(idMercadoria, idUsuario, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}