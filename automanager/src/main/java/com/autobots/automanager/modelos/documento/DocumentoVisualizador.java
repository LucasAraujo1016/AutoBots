package com.autobots.automanager.modelos.documento;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Component
public class DocumentoVisualizador {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public Documento visualizar(List<Documento> documentos, Documento documentoBusca) {
        for (Documento documento : documentos) {
            if (documento.getId() != null && documentoBusca.getId() != null) {
                if (documento.getId().equals(documentoBusca.getId())) {
                    return documento;
                }
            } else if (!verificadorNulo.verificar(documento.getTipo()) &&
                       !verificadorNulo.verificar(documento.getNumero()) &&
                       documento.getTipo().equals(documentoBusca.getTipo()) &&
                       documento.getNumero().equals(documentoBusca.getNumero())) {
                return documento;
            }
        }
        return null;
    }
}
