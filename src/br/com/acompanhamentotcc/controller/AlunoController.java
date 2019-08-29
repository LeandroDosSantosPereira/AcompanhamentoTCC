/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.AlunoDao;
import br.com.acompanhamentotcc.dao.CursoDao;
import br.com.acompanhamentotcc.model.Aluno;
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

public class AlunoController {
    private PropertyChangeSupport propertyChangeSupport 
            = new PropertyChangeSupport(this);
    private Aluno alunoDigitado;
    private Aluno alunoSelecionado;
    private AlunoDao alunoDao;
    private List<Aluno> alunosTabela;
    private List<Curso> cursosSelecao;
    private CursoDao cursoDao;
    private Curso cursoDigitado;

   
    public AlunoController() {
        this.alunoDao = new AlunoDao();
        this.cursoDao = new CursoDao();
        this.cursoDigitado = new Curso();
        this.alunosTabela = ObservableCollections.
                observableList(new ArrayList<Aluno>());
        novo();
        pesquisar();
    }

    public List<Curso> getCursosSelecao() {
        return cursosSelecao;
    }

    public void setCursosSelecao(List<Curso> cursosSelecao) {
        this.cursosSelecao = cursosSelecao;
    }
    
    public void novo(){
        setAlunoDigitado(new Aluno());
    }
    
    public void pesquisar(){
        alunosTabela.clear();
        alunosTabela.addAll(alunoDao.pesquisar(alunoDigitado));
        this.cursosSelecao = cursoDao.pesquisar(cursoDigitado);
    }
    
    public void salvar() throws Exception{
        if(alunoDigitado.getId() == null)
            alunoDao.create(alunoDigitado);
        else
            alunoDao.edit(alunoDigitado);
        novo();
        pesquisar();
    }
    
    public void excluir(){
        alunoDao.excluir(alunoDigitado);
        novo();
        pesquisar();
    }

    public Aluno getAlunoDigitado() {
        return alunoDigitado;
    }

    public void setAlunoDigitado(Aluno alunoDigitado) {
        Aluno oldAlunoDigitado = this.alunoDigitado;
        this.alunoDigitado = alunoDigitado;
        propertyChangeSupport.firePropertyChange("alunoDigitado", 
                oldAlunoDigitado,alunoDigitado);    
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
        if(this.alunoSelecionado != null){
            setAlunoDigitado(alunoSelecionado);
        }
    }

    public List<Aluno> getAlunosTabela() {
        return alunosTabela;
    }

    public void setAlunosTabela(List<Aluno> alunosTabela) {
        this.alunosTabela = alunosTabela;
    }
        
    public void addPropertyChangeListener(PropertyChangeListener pl){
        propertyChangeSupport.addPropertyChangeListener(pl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pl){        
        propertyChangeSupport.removePropertyChangeListener(pl);
    }
    
}
