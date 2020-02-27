package br.com.roavila.pontocloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.RelatorioPonto;
import br.com.roavila.pontocloud.model.Colaborador;
import br.com.roavila.pontocloud.repository.PontoRepository;
import br.com.roavila.pontocloud.service.PontoService;
import br.com.roavila.pontocloud.service.ColaboradorService;

import static br.com.roavila.pontocloud.model.Batida.ENTRADA;
import static br.com.roavila.pontocloud.model.Batida.SAIDA;

import java.time.LocalTime;
import java.util.List;

@Service
public class PontoServiceImpl implements PontoService {

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private ColaboradorService colaboradorService;

    public Ponto registrarPonto(Ponto ponto) {
        Colaborador colaborador = colaboradorService.getBy(ponto.getColaborador().getId());

        if (deveSerEntrada(colaborador)) {
            ponto.atualizaBatida(ENTRADA);
        } else {
            ponto.atualizaBatida(SAIDA);
        }
        ponto.atualizaColaborador(colaborador);

        return pontoRepository.save(ponto);
    }

    private boolean deveSerEntrada(Colaborador colaborador) {
        List<Ponto> pontos = pontoRepository.findByIdColaborador(colaborador.getId());
        if (pontos.size() % 2 == 0){
            return true;
        }
        return false;
    }

    public RelatorioPonto gerarRelatorio(Long idColaborador) {
        List<Ponto> pontos = pontoRepository.findByIdColaborador(idColaborador);
        return new RelatorioPonto(pontos, LocalTime.now());
    }
}

