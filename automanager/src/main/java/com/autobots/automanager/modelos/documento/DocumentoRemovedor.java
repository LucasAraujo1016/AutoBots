package com.autobots.automanager.modelos.documento;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Component
public class DocumentoRemovedor {
    private StringVerificadorNulo verificadorNulo = new StringVerificadorNulo();

    public void remover(Cliente cliente, Documento documento) {
        if (documento != null) {
            if (!verificadorNulo.verificar(documento.getTipo()) && !verificadorNulo.verificar(documento.getNumero())) {
                cliente.getDocumentos().remove(documento);
            }
        }
    }

    public void remover(Cliente cliente, List<Documento> documentos) {
        for (Documento documentoVazio: documentos) {
            remover(cliente, documentoVazio);
        }
    }
}
