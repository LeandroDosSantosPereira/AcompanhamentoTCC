/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.EducadorDao;
import br.com.acompanhamentotcc.model.Educador;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;

/**
 *
 *  @author LEANDRO
 */
public class EducadorController {
    private PropertyChangeSupport propertyChangeSupport 
            = new PropertyChangeSupport(this);
    private Educador educadorDigitado;
    private Educador educadorSelecionado;
    private List<Educador> educadoresTabela;
    private EducadorDao educadorDao;
    
    public EducadorController(){
        educadorDao = new EducadorDao();
        educadoresTabela = ObservableCollections.
                observableList(new ArrayList<Educador>());
        novo();
        pesquisar();
    }


    public Educador getEducadorDigitado() {
        return educadorDigitado;
    }

    public void setEducadorDigitado(Educador educadorDigitado) {
        Educador oldEducadorDigitado = this.educadorDigitado;
        this.educadorDigitado = educadorDigitado;
        propertyChangeSupport.firePropertyChange("educadorDigitado", 
                oldEducadorDigitado,educadorDigitado);
    }

    public Educador getEducadorSelecionado() {
        return educadorSelecionado;
    }

    public void setEducadorSelecionado(Educador educadorSelecionado) {
        this.educadorSelecionado = educadorSelecionado;
        if(this.educadorSelecionado != null){
            setEducadorDigitado(educadorSelecionado);
        }
    }

    public List<Educador> getEducadoresTabela() {
        return educadoresTabela;
    }

    public void setEducadoresTabela(List<Educador> educadoresTabela) {
        this.educadoresTabela = educadoresTabela;
    }
        

    public Educador getEducador() {
        return educadorDigitado;
    }

    public void setEducador(Educador educador) {
        this.educadorDigitado = educador;
    }

    public EducadorDao getEducadorDao() {
        return educadorDao;
    }

    public void setEducadorDao(EducadorDao educadorDao) {
        this.educadorDao = educadorDao;
    }

    private void novo() {
        setEducadorDigitado(new Educador());
    }

    public void pesquisar() {
        educadoresTabela.clear();
        educadoresTabela.addAll(educadorDao.pesquisar(educadorDigitado));    
    }
    
    public void salvar() throws Exception{
        if(educadorDigitado.getId() == null)
            educadorDao.create(educadorDigitado);
        else
            educadorDao.edit(educadorDigitado);
        novo();
        pesquisar();
    }
    
    public void excluir(){
        educadorDao.excluir(educadorDigitado);
        novo();
        pesquisar();
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pl){
        propertyChangeSupport.addPropertyChangeListener(pl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pl){        
        propertyChangeSupport.removePropertyChangeListener(pl);
    }
    
    
}
