package com.autobots.automanager.modelos.adicionador;

import org.springframework.stereotype.Component;
import com.autobots.automanager.controle.VendaControle;
import com.autobots.automanager.entitades.Venda;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {

    @Override
    public void adicionarLink(List<Venda> lista) {
        for (Venda venda : lista) {
            long id = venda.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(VendaControle.class)
                            .visualizarVenda(id))
                    .withSelfRel();
            venda.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Venda objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .listarVendas())
                .withRel("vendas");
        objeto.add(linkProprio);
    }

}