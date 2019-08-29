/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.UsuarioDao;
import br.com.acompanhamentotcc.model.Usuario;
import br.com.acompanhamentotcc.util.CriptografiaUtil;
import java.security.NoSuchAlgorithmException;

/**
 *
 *  @author LEANDRO
 */
public class CadastroUsuarioController {
    private Usuario usuario;
    private UsuarioDao dao;
    
    public CadastroUsuarioController(){
        usuario = new Usuario();
        dao = new UsuarioDao();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void salvar() throws NoSuchAlgorithmException{
        usuario.setPassword(
                CriptografiaUtil.encriptografarSenha(
                        usuario.getPassword()));
        dao.create(usuario);
    }
}
