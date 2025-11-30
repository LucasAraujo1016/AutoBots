package com.autobots.automanager.controles;


import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.EmpresaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
    @Autowired
    private EmpresaServico empresaServico;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa, @RequestParam("usuarioId") Long usuarioId) {
        try {
            // Supondo que você tenha um método para buscar o usuário pelo ID
            Usuario usuario = empresaServico.buscarUsuarioPorId(usuarioId);
            empresaServico.cadastrarEmpresa(empresa, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id, @RequestParam("usuarioId") Long usuarioId) {
        try {
            Usuario usuario = empresaServico.buscarUsuarioPorId(usuarioId);
            empresaServico.deletarEmpresa(id, usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa, @RequestParam("usuarioId") Long usuarioId) {
        try {
            Usuario usuario = empresaServico.buscarUsuarioPorId(usuarioId);
            ResponseEntity<?> resposta = empresaServico.atualizarEmpresa(id, empresa, usuario);
            return resposta;
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/listar")
    public ResponseEntity<List<Empresa>> listarEmpresas(@RequestParam("usuarioId") Long usuarioId) {
        Usuario usuario = empresaServico.buscarUsuarioPorId(usuarioId);
        List<Empresa> empresas = empresaServico.listarEmpresas(usuario);
        if (empresas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Empresa> visualizarEmpresa(@PathVariable Long id, @RequestParam("usuarioId") Long usuarioId) {
        Usuario usuario = empresaServico.buscarUsuarioPorId(usuarioId);
        Empresa empresa = empresaServico.visualizarEmpresa(id, usuario);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }
}