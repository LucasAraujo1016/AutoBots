package com.autobots.automanager.modelos.cadastro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@Component
public class CadastradorVeiculo {
    
    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @Autowired
    private CadastradorVenda cadastradorVenda;

    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        Veiculo veiculoNovo = new Veiculo();
        veiculoNovo.setModelo(veiculo.getModelo());
        veiculoNovo.setPlaca(veiculo.getPlaca());
        veiculoNovo.setTipo(veiculo.getTipo());
        repositorioVeiculo.save(veiculoNovo);

        if(veiculo.getProprietario() != null) {
            Usuario proprietario = new Usuario();
            proprietario.setNome(veiculo.getProprietario().getNome());
            proprietario.getVeiculos().add(veiculoNovo);
            veiculoNovo.setProprietario(proprietario);
        }

        if(veiculo.getVendas() != null) {
            for(Venda venda: veiculo.getVendas()) {
                Venda vendaAtual = cadastradorVenda.cadastrarVenda(venda);
                vendaAtual.setVeiculo(veiculoNovo);
                veiculoNovo.getVendas().add(vendaAtual);
            }
        }

        repositorioVeiculo.save(veiculoNovo);

        return veiculoNovo;
    }
}