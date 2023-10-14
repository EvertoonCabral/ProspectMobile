package com.everton.prospectmobile_trabalhoandroid.controller;

import android.content.Context;
import android.text.TextUtils;

import com.everton.prospectmobile_trabalhoandroid.dao.ClienteDao;
import com.everton.prospectmobile_trabalhoandroid.dao.EnderecoDao;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;
import com.everton.prospectmobile_trabalhoandroid.model.Endereco;

import java.util.ArrayList;

public class ClienteController {

    private Context context;


public ClienteController(Context context){

    this.context = context;

}


    public long salvarCliente(String nome, String cpf, String dataNasc, Endereco endereco) {
        // Primeiro, salvamos o endereço
        EnderecoDao enderecoDao = EnderecoDao.getInstancia(context);
        long enderecoId = enderecoDao.insert(endereco);

        // Se o endereço foi salvo com sucesso, salvamos o cliente
        if (enderecoId > 0) {
            Cliente cliente = new Cliente(0, nome, cpf, dataNasc, endereco); // 0 é um placeholder para o código do cliente, pois será gerado automaticamente pelo banco de dados.
            return ClienteDao.getInstancia(context).insert(cliente);
        } else {
            return -1;
        }
    }

    public long atualizarCliente(String codigo, String nome, String cpf, String dataNasc, Endereco codEndereco) {
        Cliente cliente = new Cliente(Integer.parseInt(codigo), nome, cpf, dataNasc, codEndereco);

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

    public String validarCliente(String nome, String cpf, String dataNasc, Endereco endereco) {
        StringBuilder validacao = new StringBuilder();

        if (TextUtils.isEmpty(nome)) {
            validacao.append("Nome do cliente deve ser preenchido!!\n");
        }

        if (TextUtils.isEmpty(cpf)) {
            validacao.append("CPF do cliente deve ser preenchido!!\n");
        }

        if (TextUtils.isEmpty(dataNasc)) {
            validacao.append("Data de Nascimento do cliente deve ser preenchida!!\n");
        }

        if (endereco == null || TextUtils.isEmpty(endereco.getLogradouro()) || TextUtils.isEmpty(endereco.getNumero()) || TextUtils.isEmpty(endereco.getBairro()) || TextUtils.isEmpty(endereco.getCidade()) || TextUtils.isEmpty(endereco.getUf())) {
            validacao.append("Todos os campos do endereço devem ser preenchidos!!\n");
        }

        // Aqui você pode adicionar outras validações específicas para cada campo, como formato do CPF, etc.

        return validacao.toString();
    }


}
