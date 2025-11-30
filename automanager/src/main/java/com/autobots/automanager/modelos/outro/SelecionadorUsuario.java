package com.autobots.automanager.modelos.outro;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.Perfil;

import java.util.ArrayList;
import java.util.List;

@Component
public class SelecionadorUsuario {

    public Usuario selecionarUsername(List<Usuario> usuarios, String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCredencial().getNomeUsuario().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> selecionarPorCargo(List<Usuario> usuarios, Perfil cargo){
        List<Usuario> filtrados = new ArrayList<Usuario>();
        for(Usuario usuario : usuarios) {
            if(usuario.getPerfis().contains(cargo)) {
                filtrados.add(usuario);
            }
        }
        return filtrados;
    }
}