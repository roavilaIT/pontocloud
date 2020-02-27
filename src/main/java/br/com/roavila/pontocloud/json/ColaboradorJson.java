package br.com.roavila.pontocloud.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.roavila.pontocloud.mapper.Json;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColaboradorJson implements Json {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataCadastro;

    public static class Builder {

        private Long id;

        private String nome;

        private String cpf;

        private String email;

        private LocalDate dataCadastro;

        public Builder(Long id){
            this.id = id;
        }

        public Builder comNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder comCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder comEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder comDataCadastro(LocalDate dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public ColaboradorJson build() {
            return new ColaboradorJson(this.id, this.nome, this.cpf, this.email, this.dataCadastro);
        }


    }

    public ColaboradorJson(Long id, String nome, String cpf, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    protected ColaboradorJson(){}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
}
