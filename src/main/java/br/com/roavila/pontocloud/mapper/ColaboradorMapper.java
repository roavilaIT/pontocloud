package br.com.roavila.pontocloud.mapper;

import br.com.roavila.pontocloud.json.ColaboradorJson;
import br.com.roavila.pontocloud.model.Colaborador;

public class ColaboradorMapper implements JsonMapper<ColaboradorJson, Colaborador> {

    @Override
    public ColaboradorJson convertModelToJson(Colaborador model) {
        return new ColaboradorJson
                    .Builder(model.getId())
                    .comNome(model.getNome())
                    .comCpf(model.getCpf())
                    .comEmail(model.getEmail())
                    .comDataCadastro(model.getDataCadastro())
                    .build();
    }
    @Override
    public Colaborador convertJsonToModel(ColaboradorJson json) {
        return new Colaborador
                    .Builder(json.getId())
                    .comNome(json.getNome())
                    .comCpf(json.getCpf())
                    .comEmail(json.getEmail())
                    .comDataCadastro(json.getDataCadastro())
                    .build();
    }


}
