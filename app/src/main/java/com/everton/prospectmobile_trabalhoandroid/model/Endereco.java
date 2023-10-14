package com.everton.prospectmobile_trabalhoandroid.model;

public class Endereco {
    private int codigo;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    public Endereco(int codigo, String logradouro, String numero, String bairro, String cidade, String uf) {
        this.codigo = codigo;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Endereco(String logradouro, String numero, String bairro, String cidade, String uf) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Endereco() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }


    @Override
    public String toString() {
        return logradouro + " Nº " + numero + " - " +uf ;
    }

    public double calcularFrete() {
        if (!this.cidade.equalsIgnoreCase("Toledo") && this.uf.equalsIgnoreCase("PR")) {
            return 20.0;
        } else if (!this.uf.equalsIgnoreCase("PR")) {
            return 50.0;
        }
        return 0;
    }
}
