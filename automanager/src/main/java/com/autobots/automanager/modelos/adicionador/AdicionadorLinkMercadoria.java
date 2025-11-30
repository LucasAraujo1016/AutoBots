package com.autobots.automanager.modelos.adicionador;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.entidades.Mercadoria;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {

    @Override
    public void adicionarLink(List<Mercadoria> lista) {
        for (Mercadoria mercadoria : lista) {
            long id = mercadoria.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(MercadoriaControle.class)
                            .visualizarMercadoria(id, null))
                    .withSelfRel();
            mercadoria.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Mercadoria objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(MercadoriaControle.class)
                        .listarMercadorias(null))
                .withRel("mercadorias");
        objeto.add(linkProprio);
    }
}