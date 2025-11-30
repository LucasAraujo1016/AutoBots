package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.cliente.ClienteSelecionador;
import com.autobots.automanager.modelos.documento.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.documento.DocumentoAtualizador;
import com.autobots.automanager.modelos.documento.DocumentoCadastrador;
import com.autobots.automanager.modelos.documento.DocumentoRemovedor;
import com.autobots.automanager.modelos.documento.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ClienteSelecionador clienteSelecionador;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private AdicionadorLinkDocumento adicionadorLinkDocumento;

    @Autowired
    private DocumentoSelecionador documentoSelecionador;

    @Autowired
    private DocumentoAtualizador documentoAtualizador;

    @Autowired
    private DocumentoCadastrador documentoCadastrador;

    @Autowired
    private DocumentoRemovedor documentoRemovedor;

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Documento> visualizarDocumento(@PathVariable long id) {
        List<Documento> documentos = documentoRepositorio.findAll();
        Documento documento = documentoSelecionador.selecionar(documentos, id);
        if (documento == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkDocumento.adicionarLink(documento);
            return new ResponseEntity<>(documento, HttpStatus.OK);
        }
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<List<Documento>> visualizarDocumentosCliente(@PathVariable long clienteid) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkDocumento.adicionarLink(cliente.getDocumentos());
            return new ResponseEntity<>(cliente.getDocumentos(), HttpStatus.OK);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Documento>> listarDocumentos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            adicionadorLinkDocumento.adicionarLink(documentos);
            return new ResponseEntity<>(documentos, HttpStatus.OK);
        }
    }

    @PostMapping("/cadastrar/{clienteid}")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody List<Documento> documentos, @PathVariable long clienteid) {
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            documentoCadastrador.cadastro(cliente, documentos);
            clienteRepositorio.save(cliente);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/remover/{clienteid}")
    public ResponseEntity<?> removerDocumento(@RequestBody List<Documento> documentos, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            documentoRemovedor.remover(cliente, documentos);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarDocumento(@RequestBody List<Documento> documentos, @PathVariable long clienteid){
        HttpStatus status = HttpStatus.CONFLICT;
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = clienteSelecionador.selecionar(clientes, clienteid);
        if (cliente == null){
            status = HttpStatus.NOT_FOUND;
        } else {
            documentoAtualizador.atualizar(cliente.getDocumentos(), documentos);
            clienteRepositorio.save(cliente);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}