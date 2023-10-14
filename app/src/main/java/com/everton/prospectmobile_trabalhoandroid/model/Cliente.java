package com.everton.prospectmobile_trabalhoandroid.model;

public class Cliente {

    private int codigo;
    private String nome;
    private String cpf;
    private String dataNasc;
    private Endereco endereco; // Atributo ajustado

    public Cliente(int codigo, String nome, String cpf, String dataNasc, Endereco endereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.endereco = endereco; // Ajustado aqui
    }

    public Cliente() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Endereco getEndereco() { // Método get ajustado
        return endereco;
    }

    public void setEndereco(Endereco endereco) { // Método set ajustado
        this.endereco = endereco;
    }
}
