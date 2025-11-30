package com.autobots.automanager.servicos;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.Perfil;
import com.autobots.automanager.modelos.adicionador.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.cadastro.CadastradorEmpresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmpresaServico {

    @Autowired
    private CadastradorEmpresa cadastradorEmpresa;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private AdicionadorLinkEmpresa adicionadorLinkEmpresa;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public void cadastrarEmpresa(Empresa empresa, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para cadastrar empresa.");
        }
        Empresa empresaCadastrada = cadastradorEmpresa.cadastrarEmpresa(empresa);
        repositorioEmpresa.save(empresaCadastrada);
    }

    public void deletarEmpresa(Long id, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para deletar empresa.");
        }
        repositorioEmpresa.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        repositorioEmpresa.deleteById(id);
    }

    public ResponseEntity<?> atualizarEmpresa(Long id, Empresa empresa, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            return ResponseEntity.status(403).body("Usuário sem permissão para atualizar empresa.");
        }
        Empresa empresaAtual = repositorioEmpresa.findById(id).orElse(null);
        if (empresaAtual != null) {
            empresaAtual.setCadastro(new Date());
            if (empresa.getRazaoSocial() != null) {
                empresaAtual.setRazaoSocial(empresa.getRazaoSocial());
            }
            if (empresa.getNomeFantasia() != null) {
                empresaAtual.setNomeFantasia(empresa.getNomeFantasia());
            }
            if (empresa.getTelefones() != null) {
                empresaAtual.setTelefones(empresa.getTelefones());
            }
            if (empresa.getEndereco() != null) {
                empresaAtual.setEndereco(empresa.getEndereco());
            }
            if (empresa.getCadastro() != null) {
                empresaAtual.setCadastro(empresa.getCadastro());
            }
            if (empresa.getUsuarios() != null) {
                empresaAtual.setUsuarios(empresa.getUsuarios());
            }
            if (empresa.getMercadorias() != null) {
                empresaAtual.setMercadorias(empresa.getMercadorias());
            }
            if (empresa.getServicos() != null) {
                empresaAtual.setServicos(empresa.getServicos());
            }
            if (empresa.getVendas() != null) {
                empresaAtual.setVendas(empresa.getVendas());
            }
            repositorioEmpresa.save(empresaAtual);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Empresa> listarEmpresas(Usuario usuarioLogado) {
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE)) {
            List<Empresa> empresas = repositorioEmpresa.findAll();
            adicionadorLinkEmpresa.adicionarLink(empresas);
            return empresas;
        }
        throw new SecurityException("Usuário sem permissão para listar empresas.");
    }

    public Empresa visualizarEmpresa(Long id, Usuario usuarioLogado) {
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE)) {
            Empresa empresa = repositorioEmpresa.findById(id).orElse(null);
            if (empresa != null) {
                adicionadorLinkEmpresa.adicionarLink(empresa);
            }
            return empresa;
        }
        throw new SecurityException("Usuário sem permissão para visualizar empresa.");
    }

    public Usuario buscarUsuarioPorId(Long usuarioId) {
        return repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + usuarioId));
    }
}