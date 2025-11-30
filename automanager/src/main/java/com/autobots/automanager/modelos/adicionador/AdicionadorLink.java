package com.autobots.automanager.modelos.adicionador;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface AdicionadorLink <T> {
    public void adicionarLink(List<T> lista);
    public void adicionarLink(T objeto);
}
