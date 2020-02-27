package br.com.roavila.pontocloud.service;

import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.RelatorioPonto;

public interface PontoService {

    public Ponto registrarPonto(Ponto ponto);

    public RelatorioPonto gerarRelatorio(Long idColaborador);
}
