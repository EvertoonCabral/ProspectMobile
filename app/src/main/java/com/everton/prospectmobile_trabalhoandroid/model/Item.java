package com.everton.prospectmobile_trabalhoandroid.model;

public class Item {

    private int codigo;
    private String descricao;
    private double ValorUnitario;
    private String unidadeMedida;


    public Item( String descricao, double valorUnitario, String unidadeMedida) {
        this.descricao = descricao;
        ValorUnitario = valorUnitario;
        this.unidadeMedida = unidadeMedida;
    }

    public Item() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnitario() {
        return ValorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.ValorUnitario = valorUnitario;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
