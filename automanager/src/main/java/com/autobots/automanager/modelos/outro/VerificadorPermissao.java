package com.autobots.automanager.modelos.outro;

import org.springframework.stereotype.Component;
import com.autobots.automanager.enumeracoes.Perfil;

import java.util.Set;

@Component
public class VerificadorPermissao {
    public boolean verificar(Set<Perfil> perfisUsuario, Set<Perfil> perfisAlvo, String operacao) {
        if (perfisUsuario.contains(Perfil.ROLE_ADMIN)) {
            return true;
        }
        if (perfisUsuario.contains(Perfil.ROLE_GERENTE)) {
            if (perfisAlvo.contains(Perfil.ROLE_ADMIN)) return false;
            if (perfisAlvo.stream().anyMatch(p -> 
                p == Perfil.ROLE_GERENTE || p == Perfil.ROLE_VENDEDOR || p == Perfil.ROLE_CLIENTE)) {
                return true;
            }
            return false;
        }
        if (perfisUsuario.contains(Perfil.ROLE_VENDEDOR)) {
            if (perfisAlvo.contains(Perfil.ROLE_CLIENTE)) {
                return true;
            }
            return false;
        }
        if (perfisUsuario.contains(Perfil.ROLE_CLIENTE)) {
            return "READ_OWN".equals(operacao);
        }
        return false;
    }
}