package com.everton.prospectmobile_trabalhoandroid.controller;

import android.content.Context;
import android.text.TextUtils;

import com.everton.prospectmobile_trabalhoandroid.dao.ClienteDao;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;

import java.util.ArrayList;

public class ClienteController {

    private Context context;


public ClienteController(Context context){

    this.context = context;

}


public long SalvarCliente(String codigo, String nome, String cpf, String dataNasc, String codEndereco){
/*

    String validacao = validarCliente(codigo, nome, cpf, dataNasc, codEndereco);
    if (!validacao.isEmpty()) {
        // Se houver erros de validação, retorna um número negativo.
        return -1;
    }
*/
    Cliente cliente = new Cliente(Integer.parseInt(codigo), nome, cpf, dataNasc, Integer.parseInt(codEndereco));
    return ClienteDao.getInstancia(context).insert(cliente);
}

    public long atualizarCliente(String codigo, String nome, String cpf, String dataNasc, String codEndereco) {
        Cliente cliente = new Cliente(Integer.parseInt(codigo), nome, cpf, dataNasc, Integer.parseInt(codEndereco));

        return ClienteDao.getInstancia(context).update(cliente);
    }

    public long apagarCliente(String codigo) {
        Cliente cliente = new Cliente();
        cliente.setCodigo(Integer.parseInt(codigo));
        return ClienteDao.getInstancia(context).delete(cliente);
    }

    public ArrayList<Cliente> retornarTodosClientes() {
        return ClienteDao.getInstancia(context).getAll();
    }

    public Cliente retornarCliente(int codigo) {
        return ClienteDao.getInstancia(context).getById(codigo);
    }

    public String validarCliente(String codigo, String nome, String cpf, String dataNasc, String codEndereco) {
        StringBuilder validacao = new StringBuilder();

        if (TextUtils.isEmpty(codigo)) {
            validacao.append("Código do cliente deve ser preenchido!!\n");
        }

        if (TextUtils.isEmpty(nome)) {
            validacao.append("Nome do cliente deve ser preenchido!!\n");
        }

        if (TextUtils.isEmpty(cpf)) {
            validacao.append("CPF do cliente deve ser preenchido!!\n");
        }

        if (TextUtils.isEmpty(dataNasc)) {
            validacao.append("Data de Nascimento do cliente deve ser preenchida!!\n");
        }

        if (TextUtils.isEmpty(codEndereco)) {
            validacao.append("Código de Endereço do cliente deve ser preenchido!!\n");
        }

        // Aqui você pode adicionar outras validações específicas para cada campo, como formato do CPF, etc.

        return validacao.toString();
    }


}
