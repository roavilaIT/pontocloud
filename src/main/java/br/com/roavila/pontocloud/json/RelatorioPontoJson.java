package br.com.roavila.pontocloud.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import br.com.roavila.pontocloud.mapper.Json;
import br.com.roavila.pontocloud.mapper.PontoMapper;
import br.com.roavila.pontocloud.model.RelatorioPonto;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelatorioPontoJson implements Json {

    private final List<PontoJson> pontos;

    private final String totalHoras;

    public RelatorioPontoJson(RelatorioPonto relatorioPonto) {
        this.pontos = relatorioPonto.getPontos().stream().map(ponto -> new PontoMapper().convertModelToJson(ponto)).collect(Collectors.toList());
        this.totalHoras = relatorioPonto.totalHoras();
    }

    protected RelatorioPontoJson() {
        this.pontos = Lists.newArrayList();
        this.totalHoras = "";
    }

    public List<PontoJson> getPontos() {
        return pontos;
    }

    public String getTotalHoras() {
        return totalHoras;
    }
}
