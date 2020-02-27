package br.com.roavila.pontocloud.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Colaborador implements Model {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nome;

    private String cpf;

    private String email;

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

        public Colaborador build() {
            return new Colaborador(this.id, this.nome, this.cpf, this.email, this.dataCadastro);
        }


    }

    public Colaborador(){}

    public Colaborador(Long id, String nome, String cpf, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    private void atualizarNome(String nome) {
        this.nome = nome;
    }

    private void atualizarCpf(String cpf) {
        this.cpf = cpf;
    }

    private void atualizarEmail(String email) {
        this.email = email;
    }

    public void atualizarDados(Colaborador colaborador) {
        atualizarNome(colaborador.getNome());
        atualizarCpf(colaborador.getCpf());
        atualizarEmail(colaborador.getEmail());
    }

}
