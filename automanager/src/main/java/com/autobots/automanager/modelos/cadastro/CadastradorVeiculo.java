package com.autobots.automanager.modelos.cadastro;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CadastradorVeiculo {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    public Veiculo cadastrarVeiculo(Veiculo veiculo){
        Veiculo veiculoNovo = new Veiculo();
        veiculoNovo.setModelo(veiculo.getModelo());
        veiculoNovo.setPlaca(veiculo.getPlaca());
        veiculoNovo.setTipo(veiculo.getTipo());

        repositorioVeiculo.save(veiculoNovo);

        if (veiculo.getProprietario() != null) {
            Usuario proprietario = new Usuario();
            proprietario.setNome(veiculo.getProprietario().getNome());
            proprietario.getVeiculos().add(veiculoNovo);
            veiculoNovo.setProprietario(proprietario);
        }
        if (veiculo.getVendas() != null) {
            for (Venda venda : veiculo.getVendas()) {
                Venda vendaAtual = cadastradorVenda.cadastrarVenda(venda);
                vendaAtual.setVeiculo(veiculoNovo);
                veiculoNovo.getVendas().add(vendaAtual);
            }
        }

        repositorioVeiculo.save(veiculoNovo);

        return veiculoNovo;
    }
}