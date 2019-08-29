/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.CursoDao;
import br.com.acompanhamentotcc.model.Curso;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;


/**
 *
 *  @author LEANDRO
 */


public class CursoController {
    private PropertyChangeSupport propertyChangeSupport 
            = new PropertyChangeSupport(this);
    private Curso cursoDigitado;
    private Curso cursoSelecionado;
    private List<Curso> cursosTabela;
    private CursoDao cursoDao;

    public CursoController() {
        cursoDao = new CursoDao();
        cursosTabela = ObservableCollections.
                observableList(new ArrayList<Curso>());
        novo();
        pesquisar();
    }
    
    public void novo(){
        setCursoDigitado(new Curso());
    }
    
    public void pesquisar(){
        cursosTabela.clear();
        cursosTabela.addAll(cursoDao.pesquisar(cursoDigitado));
    }
    
    public void salvar() throws Exception {
        if(cursoDigitado.getId() == null)
            cursoDao.create(cursoDigitado);
        else
            cursoDao.edit(cursoDigitado);
        novo();
        pesquisar();
    }
    
    public void excluir(){
        cursoDao.excluir(cursoDigitado);
        novo();
        pesquisar();
    }
    
    public Curso getCursoDigitado(){
        return cursoDigitado;
    }
    
    public void setCursoDigitado(Curso cursoDigitado){
        Curso oldCursoDigitado = this.cursoDigitado;
        this.cursoDigitado = cursoDigitado;
        propertyChangeSupport.firePropertyChange("cursoDigitado", 
                oldCursoDigitado,cursoDigitado);
    }
    
    public Curso getCursoSelecionado(){
        return cursoSelecionado;
    }
    
    public void setCursoSelecionado(Curso cursoSelecionado){
        this.cursoSelecionado = cursoSelecionado;
        if(this.cursoSelecionado != null){
            setCursoDigitado(cursoSelecionado);
        }
    }
    
    public List<Curso> getCursosTabela(){
        return cursosTabela;
    }
    
    public void setCursosTabela(List<Curso> cursosTabela){
        this.cursosTabela = cursosTabela;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pl){
        propertyChangeSupport.addPropertyChangeListener(pl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pl){        
        propertyChangeSupport.removePropertyChangeListener(pl);
    }
}
