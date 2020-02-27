package br.com.roavila.pontocloud.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class RelatorioPonto {

    private final List<Ponto> pontos;

    private String totalHoras;

    public RelatorioPonto(List<Ponto> pontos, LocalTime agora) {
        this.pontos = pontos;
        this.calcularTotalHoras(agora);
    }

    public List<Ponto> getPontos() {
        return pontos;
    }

    public String totalHoras() {
        return totalHoras;
    }

    private void calcularTotalHoras(LocalTime agora) {
        Duration tempo = Duration.ZERO;
        for (int index = 0; index < pontos.size(); index++) {
            Ponto ponto = pontos.get(index);
            if (ponto.ehEntrada()) {
                if (temProximo(index)) {
                    Ponto proximoPonto = pontos.get(index + 1);
                    if (proximoPonto.ehSaida()) {
                        tempo = tempo.plus(Duration.between(ponto.getDataHoraBatida(), proximoPonto.getDataHoraBatida()));
                    }
                } else {
                    tempo = tempo.plus(Duration.between(ponto.getDataHoraBatida(), agora));
                }
            }

        }

        long segundos = tempo.getSeconds();
        this.totalHoras = String.format("%d:%02d:%02d", segundos / 3600, (segundos % 3600) / 60, (segundos % 60));
    }

    private boolean temProximo(int index) {
        return index+1 < pontos.size();
    }
}
