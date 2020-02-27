package br.com.roavila.pontocloud.service.impl;

import com.google.common.collect.Lists;

import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.repository.ColaboradorRepository;
import br.com.roavila.pontocloud.service.ColaboradorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    @Autowired
    private ColaboradorRepository repository;


    @Override
    public Colaborador criar(Colaborador colaborador) {
        return repository.save(colaborador);
    }

    @Override
    public List<Colaborador> listar() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Colaborador atualizar(Colaborador newColaborador, Long id) {
        return repository.findById(id).map((colaboradorListado) -> {
            colaboradorListado.atualizarDados(newColaborador);
            return repository.save(colaboradorListado);
        }).orElseGet(() -> {
            newColaborador.setId(id);
            return repository.save(newColaborador);
        });
    }

    @Override
    public Colaborador getBy(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Usuário {0} não encontrado", id)));
    }
}
