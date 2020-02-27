package br.com.roavila.pontocloud.model;

import javax.persistence.*;

import static br.com.roavila.pontocloud.model.Batida.ENTRADA;

import java.time.LocalTime;

@Entity
public class Ponto implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalTime dataHoraBatida;

    @ManyToOne
    private Colaborador colaborador;

    @Enumerated(EnumType.STRING)
    private Batida batida;

    protected Ponto(){}

    public Ponto(Long id, LocalTime dataHoraBatida, Colaborador colaborador, Batida batida) {
        this.id = id;
        this.dataHoraBatida = dataHoraBatida;
        this.colaborador = colaborador;
        this.batida = batida;
    }

    public static class Builder {

        private Long id;

        private LocalTime dataHoraBatida;

        private Colaborador colaborador;

        private Batida batida;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder comColaborador(Colaborador colaborador) {
            this.colaborador = colaborador;
            return this;
        }

        public Builder comBatida(Batida batida) {
            this.batida = batida;
            return this;
        }

        public Builder comHora(LocalTime dataHoraBatida) {
            this.dataHoraBatida = dataHoraBatida;
            return this;
        }

        public Ponto build() {
            return new Ponto(this.id, this.dataHoraBatida, this.colaborador, this.batida);
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public Batida getBatida() {
        return batida;
    }

    public void atualizaDataHoraBatida(LocalTime dataHoraBatida) {
        this.dataHoraBatida = dataHoraBatida;
    }

    public void atualizaBatida(Batida batida) {
        this.batida = batida;
    }

    public void atualizaColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void atualizarDados(Ponto ponto) {
        atualizaColaborador(ponto.getColaborador());
        atualizaBatida(ponto.getBatida());
        atualizaDataHoraBatida(ponto.getDataHoraBatida());
    }

    public boolean ehEntrada() {
        return this.batida.equals(ENTRADA);
    }

    public boolean ehSaida() {
        return this.batida.equals(Batida.SAIDA);
    }
}
