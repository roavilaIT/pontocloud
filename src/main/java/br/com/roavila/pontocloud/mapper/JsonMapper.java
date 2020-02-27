package br.com.roavila.pontocloud.mapper;


import br.com.roavila.pontocloud.model.Model;

public interface JsonMapper<J extends Json, M extends Model> {

    public M convertJsonToModel(J json);

    public J convertModelToJson(M model);
}
