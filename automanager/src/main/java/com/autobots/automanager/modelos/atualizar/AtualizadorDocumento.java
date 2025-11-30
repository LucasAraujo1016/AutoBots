package com.autobots.automanager.modelos.atualizar;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entitades.Documento;

@Component
public class AtualizadorDocumento {
    public Documento atualizarDocumento(Documento documento, Documento setDocumento) {
        documento.setTipo(setDocumento.getTipo());
        documento.setNumero(setDocumento.getNumero());
        documento.setDataEmissao(new Date());
        return documento;
    }
}
