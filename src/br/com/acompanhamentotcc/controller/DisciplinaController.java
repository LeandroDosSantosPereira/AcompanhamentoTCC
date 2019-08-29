/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.CursoDao;
import br.com.acompanhamentotcc.dao.DisciplinaDao;
import br.com.acompanhamentotcc.model.Curso;
import br.com.acompanhamentotcc.model.Disciplina;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;



/**
 *
 *  @author LEANDRO
 */


public class DisciplinaController {
    private PropertyChangeSupport propertyChangeSupport 
            = new PropertyChangeSupport(this);
    private Disciplina disciplinaDigitada;
    private Disciplina disciplinaSelecionada;
    private List<Disciplina> disciplinasTabela;
    private DisciplinaDao disciplinaDao;
    private Curso curso;
    private List<Curso> cursosTabela;
    private CursoDao cursoDao;
    
    
    public DisciplinaController(){
        disciplinaDao = new DisciplinaDao();
        cursoDao = new CursoDao();
        disciplinasTabela = ObservableCollections.
                observableList(new ArrayList<Disciplina>());
        novo();
        pesquisar();
    }
    
    public void novo(){
        setDisciplinaDigitada(new Disciplina());
        curso = new Curso();
    }
    
    public void pesquisar(){
        disciplinasTabela.clear();
        disciplinasTabela.addAll(disciplinaDao.pesquisar(disciplinaDigitada));
        this.cursosTabela = cursoDao.pesquisar(curso);
    }

    public List<Curso> getCursosTabela() {
        return cursosTabela;
    }
        
    public void salvar() throws Exception {
        if(disciplinaDigitada.getId() == null)
            disciplinaDao.create(disciplinaDigitada);
        else
            disciplinaDao.edit(disciplinaDigitada);
        novo();
        pesquisar();
    }
    
    public void excluir(){
        disciplinaDao.excluir(disciplinaDigitada);
        novo();
        pesquisar();
    }

    public void addPropertyChangeListener(PropertyChangeListener pl){
        propertyChangeSupport.addPropertyChangeListener(pl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pl){        
        propertyChangeSupport.removePropertyChangeListener(pl);
    }

    public Disciplina getDisciplinaDigitada() {
        return disciplinaDigitada;
    }

    public void setDisciplinaDigitada(Disciplina disciplinaDigitada) {
        Disciplina oldDisciplinaDigitada = this.disciplinaDigitada;
        this.disciplinaDigitada = disciplinaDigitada;
        propertyChangeSupport.firePropertyChange("disciplinaDigitada", 
                oldDisciplinaDigitada,disciplinaDigitada);
    }

    
    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    
    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
        if(this.disciplinaSelecionada != null){
            setDisciplinaDigitada(disciplinaSelecionada);
        }
    }

    public List<Disciplina> getDisciplinasTabela() {
        return disciplinasTabela;
    }

    public void setDisciplinasTabela(List<Disciplina> disciplinasTabela) {
        this.disciplinasTabela = disciplinasTabela;
    }
    
    public Disciplina getDisciplina() {
        return disciplinaDigitada;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplinaDigitada = disciplina;
    }    
    
}
