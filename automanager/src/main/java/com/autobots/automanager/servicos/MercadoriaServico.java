package com.autobots.automanager.servicos;

import com.autobots.automanager.DTO.MercadoriaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.Perfil;
import com.autobots.automanager.modelos.adicionador.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelos.cadastro.CadastradorMercadoria;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MercadoriaServico {

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private CadastradorMercadoria cadastradorMercadoria;

    @Autowired
    private AdicionadorLinkMercadoria adicionadorLinkMercadoria;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;


    public void cadastrarMercadoria(MercadoriaDTO mercadoria, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para cadastrar mercadoria.");
        }
        Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
        repositorioMercadoria.save(mercadoriaCadastrada);
    }

    public void cadastrarMercadoriaEmpresa(MercadoriaDTO mercadoria, Long idEmpresa, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para cadastrar mercadoria para empresa.");
        }
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
        repositorioMercadoria.save(mercadoriaCadastrada);
        empresa.getMercadorias().add(mercadoriaCadastrada);
        repositorioEmpresa.save(empresa);
    }

    public void cadastrarMercadoriaUsuario(MercadoriaDTO mercadoria, Long idUsuario, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para cadastrar mercadoria para usuário.");
        }
        Usuario usuario  = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        Mercadoria mercadoriaCadastrada = cadastradorMercadoria.cadastrarMercadoria(mercadoria);
        repositorioMercadoria.save(mercadoriaCadastrada);
        usuario.getMercadorias().add(mercadoriaCadastrada);
        repositorioUsuario.save(usuario);
    }

    public void deletarMercadoria(Long id, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para deletar mercadoria.");
        }
        Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }

        List<Usuario> usuarios = repositorioUsuario.findAll();
        for (Usuario usuario : usuarios) {
            usuario.getMercadorias().remove(mercadoria);
            repositorioUsuario.save(usuario);
        }
        List<Empresa> empresas = repositorioEmpresa.findAll();
        for (Empresa empresa : empresas) {
            empresa.getMercadorias().remove(mercadoria);
            repositorioEmpresa.save(empresa);
        }
        List<Venda> vendas = repositorioVenda.findAll();
        for (Venda venda : vendas) {
            venda.getMercadorias().remove(mercadoria);
            repositorioVenda.save(venda);
        }
        repositorioMercadoria.deleteById(id);
    }

    public void atualizarMercadoria(Long id, Mercadoria mercadoria, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para atualizar mercadoria.");
        }
        Mercadoria mercadoriaAtual = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoriaAtual != null) {
            if (mercadoria.getValidade() != null) {
                mercadoriaAtual.setValidade(mercadoria.getValidade());
            }
            if (mercadoria.getFabricao() != null) {
                mercadoriaAtual.setFabricao(mercadoria.getFabricao());
            }
            if (mercadoria.getCadastro() != null) {
                mercadoriaAtual.setCadastro(mercadoria.getCadastro());
            }
            if (mercadoria.getNome() != null) {
                mercadoriaAtual.setNome(mercadoria.getNome());
            }
            if (mercadoria.getQuantidade() != 0) {
                mercadoriaAtual.setQuantidade(mercadoria.getQuantidade());
            }
            if (mercadoria.getValor() != 0) {
                mercadoriaAtual.setValor(mercadoria.getValor());
            }
            if (mercadoria.getDescricao() != null) {
                mercadoriaAtual.setDescricao(mercadoria.getDescricao());
            }
            repositorioMercadoria.save(mercadoriaAtual);
        } else {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
    }

    public List<Mercadoria> listarMercadorias(Usuario usuarioLogado) {
        // ADMIN, GERENTE e VENDEDOR podem listar
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
            List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
            adicionadorLinkMercadoria.adicionarLink(mercadorias);
            return mercadorias;
        }
        throw new SecurityException("Usuário sem permissão para listar mercadorias.");
    }

    public Mercadoria visualizarMercadoria(Long id, Usuario usuarioLogado) {
        Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
        if (mercadoria != null) {
            // ADMIN, GERENTE e VENDEDOR podem visualizar
            if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
                usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
                usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
                adicionadorLinkMercadoria.adicionarLink(mercadoria);
                return mercadoria;
            }
            throw new SecurityException("Usuário sem permissão para visualizar mercadoria.");
        }
        return null;
    }

    public List<Mercadoria> visualizarMercadoriaEmpresa(Long idEmpresa, Usuario usuarioLogado) {
        // ADMIN, GERENTE e VENDEDOR podem visualizar
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
            Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
            if (empresa == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
            Set<Mercadoria> mercadorias = empresa.getMercadorias();
            List<Mercadoria> mercadoriasLista = new ArrayList<>(mercadorias);
            adicionadorLinkMercadoria.adicionarLink(mercadoriasLista);
            return mercadoriasLista;
        }
        throw new SecurityException("Usuário sem permissão para visualizar mercadorias da empresa.");
    }

    public List<Mercadoria> visualizarMercadoriaUsuario(Long idUsuario, Usuario usuarioLogado) {
        // ADMIN, GERENTE e VENDEDOR podem visualizar mercadorias de qualquer usuário
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
            Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
            if (usuario == null) {
                throw new IllegalArgumentException("Usuario não encontrado");
            }
            Set<Mercadoria> mercadorias = usuario.getMercadorias();
            List<Mercadoria> mercadoriasLista = new ArrayList<>(mercadorias);
            adicionadorLinkMercadoria.adicionarLink(mercadoriasLista);
            return mercadoriasLista;
        }
        throw new SecurityException("Usuário sem permissão para visualizar mercadorias do usuário.");
    }

    public void vincularMercadoriaEmpresa(Long idMercadoria, Long idEmpresa, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para vincular mercadoria à empresa.");
        }
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getMercadorias().add(mercadoria);
        repositorioEmpresa.save(empresa);
    }

    public void vincularMercadoriaUsuario(Long idMercadoria, Long idUsuario, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para vincular mercadoria ao usuário.");
        }
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        usuario.getMercadorias().add(mercadoria);
        repositorioUsuario.save(usuario);
    }

    public void desvincularMercadoriaEmpresa(Long idMercadoria, Long idEmpresa, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para desvincular mercadoria da empresa.");
        }
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        empresa.getMercadorias().remove(mercadoria);
        repositorioEmpresa.save(empresa);
    }

    public void desvincularMercadoriaUsuario(Long idMercadoria, Long idUsuario, Usuario usuarioLogado) {
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            throw new SecurityException("Usuário sem permissão para desvincular mercadoria do usuário.");
        }
        Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (mercadoria == null) {
            throw new IllegalArgumentException("Mercadoria não encontrada");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não encontrado");
        }
        usuario.getMercadorias().remove(mercadoria);
        repositorioUsuario.save(usuario);
    }
}