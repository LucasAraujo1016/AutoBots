package com.autobots.automanager.controles;

import com.autobots.automanager.DTO.AtualizarUsuarioDTO;
import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuario, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.cadastrarUsuario(username, usuario);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PostMapping("/cadastrar/empresa/{idEmpresa}")
    public ResponseEntity<?> cadastrarUsuarioEmpresa(@RequestBody UsuarioDTO usuario, @PathVariable Long idEmpresa, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.cadastrarUsuarioEmpresa(usuario, idEmpresa, username);
            return ResponseEntity.created(null).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.deletarUsuario(id, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizarUsuarioDTO usuario, Authentication authentication) {
        String username = authentication.getName();
        ResponseEntity<?> resposta = usuarioService.atualizarUsuario(id, usuario, username);
        return resposta;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(usuarios, HttpStatus.FOUND);
        }
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/me")
    public ResponseEntity<Usuario> visualizarProprioCadastro(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.visualizarUsuarioPorUsername(username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Usuario> visualizarUsuario(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.visualizarUsuario(id, username);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/vincular/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<?> vincularUsuarioEmpresa(@PathVariable Long idUsuario, @PathVariable Long idEmpresa, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.vincularUsuarioEmpresa(idUsuario, idEmpresa, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    @PutMapping("/desvincular/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<?> desvincularUsuarioEmpresa(@PathVariable Long idUsuario, @PathVariable Long idEmpresa, Authentication authentication) {
        String username = authentication.getName();
        try {
            usuarioService.desvincularUsuarioEmpresa(idUsuario, idEmpresa, username);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}