package br.com.roavila.pontocloud.service;

import java.util.List;

import br.com.roavila.pontocloud.model.Colaborador;

public interface ColaboradorService {

    public Colaborador criar(Colaborador colaborador);

    public List<Colaborador> listar();

    public Colaborador atualizar(Colaborador colaborador, Long id);

    public Colaborador getBy(Long id);

}
