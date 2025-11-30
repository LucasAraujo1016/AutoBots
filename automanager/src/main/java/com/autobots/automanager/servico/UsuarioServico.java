package com.autobots.automanager.servico;

import com.autobots.automanager.DTO.AtualizarUsuarioDTO;
import com.autobots.automanager.DTO.UsuarioDTO;
import com.autobots.automanager.entitades.*;
import com.autobots.automanager.modelos.adicionador.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.atualizar.AtualizadorDocumento;
import com.autobots.automanager.modelos.atualizar.AtualizadorEmail;
import com.autobots.automanager.modelos.atualizar.AtualizadorTelefone;
import com.autobots.automanager.modelos.cadastro.CadastradorUsuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServico {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLinkUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private AtualizadorEmail atualizadorEmail;

    @Autowired
    private AtualizadorDocumento atualizadorDocumento;

    @Autowired
    private AtualizadorTelefone atualizadorTelefone;

    public void cadastrarUsuario(UsuarioDTO usuario) {
        Usuario usuarioCadastrado = CadastradorUsuario.cadastrarUsuario(usuario);
        repositorioUsuario.save(usuarioCadastrado);
    }

    public void cadastrarUsuarioEmpresa(UsuarioDTO usuario, Long idEmpresa) {
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Usuario usuarioCadastrado = CadastradorUsuario.cadastrarUsuario(usuario);
        Usuario usuarioSalvo = repositorioUsuario.save(usuarioCadastrado);
        empresa.getUsuarios().add(usuarioSalvo);
        repositorioEmpresa.save(empresa);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        List<Venda> vendas = repositorioVenda.findAll();
        for (Venda venda : vendas) {
            if (venda.getCliente() == usuario) {
                venda.setCliente(null);
            }
            if (venda.getFuncionario() == usuario) {
                venda.setFuncionario(null);
            }
            usuario.getVendas().remove(venda);
        }

        List<Veiculo> veiculos = repositorioVeiculo.findAll();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getProprietario() == usuario) {
                veiculo.setProprietario(null);
            }
        }

        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getUsuarios().remove(usuario);
        }

        usuario.getDocumentos().clear();

        usuario.setEndereco(null);

        usuario.getTelefones().clear();

        usuario.getEmails().clear();

        usuario.getCredenciais().clear();

        usuario.getMercadorias().clear();

        repositorioUsuario.deleteById(id);
    }

    public ResponseEntity<?> atualizarUsuario(Long id, AtualizarUsuarioDTO usuario) {
        Usuario usuarioAtual = repositorioUsuario.findById(id).orElse(null);
        if (usuarioAtual != null) {
            if (usuario.nome().isPresent()) {
                usuarioAtual.setNome(usuario.nome().get());
            }
            if (usuario.nomeSocial().isPresent()) {
                usuarioAtual.setNomeSocial(usuario.nomeSocial().get());
            }
            if (usuario.perfis().isPresent()) {
                usuarioAtual.setPerfis(usuario.perfis().get());
            }
            if (usuario.telefones().isPresent()) {
                for (Telefone telefone : usuario.telefones().get()) {
                    for (Telefone telefoneAtual : usuarioAtual.getTelefones()) {
                        if (telefone.getId().equals(telefoneAtual.getId())) {
                            atualizadorTelefone.atualizarTelefone(telefoneAtual, telefone);
                        }
                    }
                }
            }
            if (usuario.endereco().isPresent()) {
                usuarioAtual.setEndereco(usuario.endereco().get());
            }
            if (usuario.documentos().isPresent()) {
                for (Documento documento : usuario.documentos().get()) {
                    for (Documento documentoAtual : usuarioAtual.getDocumentos()) {
                        if (documento.getId().equals(documentoAtual.getId())) {
                            atualizadorDocumento.atualizarDocumento(documentoAtual, documento);
                        }
                    }
                }
            }
            if (usuario.emails().isPresent()) {
                for (Email email : usuario.emails().get()) {
                    for (Email emailAtual : usuarioAtual.getEmails()) {
                        if (email.getId().equals(emailAtual.getId())) {
                            atualizadorEmail.atualizarEmail(emailAtual, email);
                        }
                    }
                }
            }
            if (usuario.credenciais().isPresent()) {
                usuarioAtual.getCredenciais().addAll(usuario.credenciais().get());
            }
            if (usuario.mercadorias().isPresent()) {
                usuarioAtual.getMercadorias().addAll(usuario.mercadorias().get());
            }
            if (usuario.vendas().isPresent()) {
                usuarioAtual.getVendas().addAll(usuario.vendas().get());
            }
            if (usuario.veiculos().isPresent()) {
                usuarioAtual.getVeiculos().addAll(usuario.veiculos().get());
            }
            repositorioUsuario.save(usuarioAtual);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.findAll();
        adicionadorLinkUsuario.adicionarLink(usuarios);
        return usuarios;
    }

    public Usuario visualizarUsuario(Long id) {
        Usuario usuario = repositorioUsuario.findById(id).orElse(null);
        if (usuario != null) {
            adicionadorLinkUsuario.adicionarLink(usuario);
        }
        return usuario;
    }

    public void vincularUsuarioEmpresa(Long idUsuario, Long idEmpresa) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().add(usuario);
        repositorioEmpresa.save(empresa);
    }

    public void desvincularUsuarioEmpresa(Long idUsuario, Long idEmpresa) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getUsuarios().remove(usuario);
        repositorioEmpresa.save(empresa);
    }
}