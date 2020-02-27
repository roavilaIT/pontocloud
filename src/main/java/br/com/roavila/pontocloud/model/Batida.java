package br.com.roavila.pontocloud.model;

public enum Batida {

    ENTRADA("E"), SAIDA("S");

    private Batida(String tipo) {
        this.tipo = tipo;
    }

    private String tipo;

    public String getTipo() {
        return this.tipo;
    }

    public static Batida fromTipo(String tipo) {
        Batida batidaFiltrada = Batida.ENTRADA;
        for (Batida batida : Batida.values()) {
            if (batida.tipo.equalsIgnoreCase(tipo)) {
                batidaFiltrada = batida;
                break;
            }
        }
        return batidaFiltrada;
    }

}
