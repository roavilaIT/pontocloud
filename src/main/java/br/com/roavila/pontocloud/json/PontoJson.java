package br.com.roavila.pontocloud.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.roavila.pontocloud.mapper.Json;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PontoJson implements Json {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonIgnore
    private LocalTime dataHoraBatida;

    private Long colaborador;

    private String batida;


    public static class Builder {

        private Long id;

        private Long colaborador;

        private String batida;

        private LocalTime dataHoraBatida;

        public Builder(Long id){
            this.id = id;
        }

        public Builder comColaborador(Long colaborador) {
            this.colaborador = colaborador;
            return this;
        }

        public Builder comBatida(String batida) {
            this.batida = batida;
            return this;
        }

        public Builder comHoraBatida(LocalTime dataHoraBatida) {
            this.dataHoraBatida = dataHoraBatida;
            return this;
        }

        public PontoJson build() {
            return new PontoJson(this.id, this.colaborador, this.batida, this.dataHoraBatida);
        }


    }

    public PontoJson(Long id, Long colaborador, String batida, LocalTime dataHoraBatida) {
        this.id = id;
        this.colaborador = colaborador;
        this.batida = batida;
        this.dataHoraBatida = dataHoraBatida;
    }

    protected PontoJson(){}

    public Long getId() {
        return id;
    }

    public LocalTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public Long getColaborador() {
        return colaborador;
    }

    public String getBatida() {
        return batida;
    }
}
