package br.com.roavila.pontocloud.mapper;

import java.time.LocalTime;

import br.com.roavila.pontocloud.json.PontoJson;
import br.com.roavila.pontocloud.model.Ponto;
import br.com.roavila.pontocloud.model.Colaborador;

public class PontoMapper implements JsonMapper<PontoJson, Ponto> {

    @Override
    public Ponto convertJsonToModel(PontoJson json) {
        return new Ponto
                    .Builder(json.getId())
                    .comColaborador(new Colaborador.Builder(json.getColaborador()).build())
                    .comHora(LocalTime.now())
                    .build();
    }

    @Override
    public PontoJson convertModelToJson(Ponto model) {
        return new PontoJson
                    .Builder(model.getId())
                    .comColaborador(model.getColaborador().getId())
                    .comBatida(model.getBatida().getTipo())
                    .comHoraBatida(model.getDataHoraBatida())
                    .build();
    }
}
