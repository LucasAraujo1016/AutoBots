package com.autobots.automanager.servicos;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.Perfil;
import com.autobots.automanager.modelos.adicionador.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.cadastro.CadastradorVeiculo;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VeiculoServico {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private CadastradorVeiculo cadastradorVeiculo;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;

    public ResponseEntity<?> cadastrarVeiculo(Veiculo veiculo, Usuario usuarioLogado) {
        // Apenas ADMIN e GERENTE podem cadastrar veículos
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            return ResponseEntity.status(403).body("Usuário sem permissão para cadastrar veículo.");
        }
        Veiculo veiculoCadastrado = cadastradorVeiculo.cadastrarVeiculo(veiculo);
        repositorioVeiculo.save(veiculoCadastrado);
        return ResponseEntity.created(null).build();
    }

    public ResponseEntity<?> excluirVeiculo(Long id, Usuario usuarioLogado) {
        Veiculo veiculo = repositorioVeiculo.findById(id).orElse(null);
        if (veiculo != null) {
            // Apenas ADMIN e GERENTE podem excluir veículos
            if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
                return ResponseEntity.status(403).body("Usuário sem permissão para excluir veículo.");
            }
            veiculo.getProprietario().getVeiculos().remove(veiculo);
            repositorioUsuario.save(veiculo.getProprietario());

            for (Venda venda : veiculo.getVendas()) {
                venda.setVeiculo(null);
                repositorioVenda.save(venda);
            }

            repositorioVeiculo.delete(veiculo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> atualizarVeiculo(Long veiculoId, Veiculo veiculo, Usuario usuarioLogado) {
        Veiculo veiculoAtualizado = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculoAtualizado != null) {
            // Apenas ADMIN e GERENTE podem atualizar veículos
            if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
                return ResponseEntity.status(403).body("Usuário sem permissão para atualizar veículo.");
            }
            if (veiculo.getModelo() != null) {
                veiculoAtualizado.setModelo(veiculo.getModelo());
            }
            if (veiculo.getPlaca() != null) {
                veiculoAtualizado.setPlaca(veiculo.getPlaca());
            }
            if (veiculo.getTipo() != null) {
                veiculoAtualizado.setTipo(veiculo.getTipo());
            }
            if (veiculo.getProprietario() != null) {
                veiculoAtualizado.setProprietario(veiculo.getProprietario());
            }
            if (veiculo.getVendas() != null) {
                veiculoAtualizado.setVendas(veiculo.getVendas());
            }
            repositorioVeiculo.save(veiculoAtualizado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Veiculo> listarVeiculos(Usuario usuarioLogado) {
        // ADMIN, GERENTE e VENDEDOR podem listar todos
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
            List<Veiculo> veiculos = repositorioVeiculo.findAll();
            adicionadorLinkVeiculo.adicionarLink(veiculos);
            return veiculos;
        }
        // CLIENTE não pode listar todos os veículos
        throw new SecurityException("Usuário sem permissão para listar veículos.");
    }

    public Veiculo visualizarVeiculo(Long id, Usuario usuarioLogado) {
        Veiculo veiculo = repositorioVeiculo.findById(id).orElse(null);
        if (veiculo != null) {
            // ADMIN, GERENTE e VENDEDOR podem visualizar qualquer veículo
            if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
                usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
                usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
                adicionadorLinkVeiculo.adicionarLink(veiculo);
                return veiculo;
            }
            // CLIENTE pode visualizar apenas seus próprios veículos
            if (usuarioLogado.getPerfis().contains(Perfil.ROLE_CLIENTE) &&
                veiculo.getProprietario() != null &&
                veiculo.getProprietario().getId().equals(usuarioLogado.getId())) {
                adicionadorLinkVeiculo.adicionarLink(veiculo);
                return veiculo;
            }
            throw new SecurityException("Usuário sem permissão para visualizar este veículo.");
        }
        return null;
    }

    public List<Veiculo> listarVeiculosUsuario(Long idUsuario, Usuario usuarioLogado) {
        Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        // ADMIN, GERENTE e VENDEDOR podem listar veículos de qualquer usuário
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE) ||
            usuarioLogado.getPerfis().contains(Perfil.ROLE_VENDEDOR)) {
            Set<Veiculo> veiculos = usuario.getVeiculos();
            List<Veiculo> veiculosLista = new ArrayList<>(veiculos);
            adicionadorLinkVeiculo.adicionarLink(veiculosLista);
            return veiculosLista;
        }
        // CLIENTE só pode listar seus próprios veículos
        if (usuarioLogado.getPerfis().contains(Perfil.ROLE_CLIENTE) &&
            usuarioLogado.getId().equals(idUsuario)) {
            Set<Veiculo> veiculos = usuario.getVeiculos();
            List<Veiculo> veiculosLista = new ArrayList<>(veiculos);
            adicionadorLinkVeiculo.adicionarLink(veiculosLista);
            return veiculosLista;
        }
        throw new SecurityException("Usuário sem permissão para listar veículos deste usuário.");
    }

    public ResponseEntity<?> vincularVeiculoUsuario(Long veiculoId, Long usuarioId, Usuario usuarioLogado) {
        // Apenas ADMIN e GERENTE podem vincular veículos
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            return ResponseEntity.status(403).body("Usuário sem permissão para vincular veículo.");
        }
        Veiculo veiculo = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = repositorioUsuario.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            veiculo.setProprietario(usuario);
            repositorioVeiculo.save(veiculo);
            usuario.getVeiculos().add(veiculo);
            repositorioUsuario.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> desvincularVeiculoUsuario(Long veiculoId, Long usuarioId, Usuario usuarioLogado) {
        // Apenas ADMIN e GERENTE podem desvincular veículos
        if (!(usuarioLogado.getPerfis().contains(Perfil.ROLE_ADMIN) || usuarioLogado.getPerfis().contains(Perfil.ROLE_GERENTE))) {
            return ResponseEntity.status(403).body("Usuário sem permissão para desvincular veículo.");
        }
        Veiculo veiculo = repositorioVeiculo.findById(veiculoId).orElse(null);
        if (veiculo != null) {
            Usuario usuario = repositorioUsuario.findById(usuarioId).orElse(null);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            veiculo.setProprietario(null);
            repositorioVeiculo.save(veiculo);
            usuario.getVeiculos().remove(veiculo);
            repositorioUsuario.save(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}