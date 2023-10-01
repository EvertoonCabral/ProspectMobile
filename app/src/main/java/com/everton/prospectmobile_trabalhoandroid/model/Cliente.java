package com.everton.prospectmobile_trabalhoandroid.model;

public class Cliente {

    private int codigo;
    private String nome;
    private String cpf;
    private String dataNasc;
    private int CodEndereco;


    public Cliente(int codigo, String nome, String cpf, String dataNasc, int codEndereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        CodEndereco = codEndereco;
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

    public int getCodEndereco() {
        return CodEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        CodEndereco = codEndereco;
    }
}
