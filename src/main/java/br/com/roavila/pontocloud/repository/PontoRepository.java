package br.com.roavila.pontocloud.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.Colaborador;

import java.util.List;

public interface PontoRepository extends CrudRepository<Ponto, Long> {

    public List<Ponto> findByColaborador(Colaborador colaborador);

    @Query("select p from Ponto p where p.colaborador.id = ?1")
    public List<Ponto> findByIdColaborador(Long idColaborador);
}
