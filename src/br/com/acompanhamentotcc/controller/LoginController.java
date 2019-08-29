package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.UsuarioDao;
import br.com.acompanhamentotcc.model.Usuario;
import br.com.acompanhamentotcc.util.CriptografiaUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 *  @author LEANDRO
 */
public class LoginController {
    private Usuario usuario;
    private UsuarioDao dao;
    
    public LoginController(){
        usuario = new Usuario();
        dao = new UsuarioDao();
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public boolean efetuarLogin() throws NoSuchAlgorithmException{
        usuario.setPassword(retornaPasswordEncriptografado());
        if(dao.efetuarLogin(usuario) != null)
            return true;
        else return false;
    }

    private String retornaPasswordEncriptografado() throws NoSuchAlgorithmException {
        return CriptografiaUtil.encriptografarSenha(usuario.getPassword());
    }
    
    
}
