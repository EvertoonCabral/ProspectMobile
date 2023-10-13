package com.everton.prospectmobile_trabalhoandroid.controller;


import android.content.Context;
import android.text.TextUtils;
import com.everton.prospectmobile_trabalhoandroid.model.Endereco;

import com.everton.prospectmobile_trabalhoandroid.dao.EnderecoDao;

import java.util.ArrayList;

public class EnderecoController {

        private Context context;

        public EnderecoController(Context context) {
            this.context = context;
        }

        public long salvarEndereco(String codigo, String logradouro, String numero, String bairro, String cidade, String uf) {
            String validacao = validarEndereco(codigo, logradouro, numero, bairro, cidade, uf);
            if (!validacao.isEmpty()) {
                // Se houver erros de validação, retorna um número negativo.
                return -1;
            }

            Endereco endereco = new Endereco(Integer.parseInt(codigo), logradouro, numero, bairro, cidade, uf);
            return EnderecoDao.getInstancia(context).insert(endereco);
        }

        public long atualizarEndereco(String codigo, String logradouro, String numero, String bairro, String cidade, String uf) {
            Endereco endereco = new Endereco(Integer.parseInt(codigo), logradouro, numero, bairro, cidade, uf);
            return EnderecoDao.getInstancia(context).update(endereco);
        }

        public long apagarEndereco(String codigo) {
            Endereco endereco = new Endereco();
            endereco.setCodigo(Integer.parseInt(codigo));
            return EnderecoDao.getInstancia(context).delete(endereco);
        }

        public ArrayList<Endereco> retornarTodosEnderecos() {
            return EnderecoDao.getInstancia(context).getAll();
        }

        public Endereco retornarEndereco(int codigo) {
            return EnderecoDao.getInstancia(context).getById(codigo);
        }

        public String validarEndereco(String codigo, String logradouro, String numero, String bairro, String cidade, String uf) {
            StringBuilder validacao = new StringBuilder();

            if (TextUtils.isEmpty(codigo)) {
                validacao.append("Código do endereço deve ser preenchido!!\n");
            }

            if (TextUtils.isEmpty(logradouro)) {
                validacao.append("Logradouro deve ser preenchido!!\n");
            }

            if (TextUtils.isEmpty(numero)) {
                validacao.append("Número do endereço deve ser preenchido!!\n");
            }

            if (TextUtils.isEmpty(bairro)) {
                validacao.append("Bairro deve ser preenchido!!\n");
            }

            if (TextUtils.isEmpty(cidade)) {
                validacao.append("Cidade deve ser preenchida!!\n");
            }

            if (TextUtils.isEmpty(uf)) {
                validacao.append("UF deve ser preenchido!!\n");
            }

            // Aqui você pode adicionar outras validações específicas para cada campo.

            return validacao.toString();
        }
    }


