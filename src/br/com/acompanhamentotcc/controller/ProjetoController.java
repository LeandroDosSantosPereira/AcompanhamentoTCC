/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.controller;

import br.com.acompanhamentotcc.dao.AlunoDao;
import br.com.acompanhamentotcc.dao.EducadorDao;
import br.com.acompanhamentotcc.dao.ProjetoDao;
import br.com.acompanhamentotcc.model.Aluno;
import br.com.acompanhamentotcc.model.Educador;
import br.com.acompanhamentotcc.model.Projeto;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;

/**
 *
 *  @author LEANDRO
 */
public class ProjetoController {
    private PropertyChangeSupport propertyChangeSupport 
            = new PropertyChangeSupport(this);
    private Projeto projetoDigitado;
    private Projeto projetoSelecionado;
    private ProjetoDao projetoDao;
    private List<Projeto> projetos;
    private List<Educador> orientadores;
    private List<Educador> coorientadores;
    private List<Aluno> alunos;
    private EducadorDao educadorDao;
    private AlunoDao alunoDao;
    private Aluno aluno;
    private Educador orientador;
    private List<Aluno> alunosProjeto;
    private List<Educador> coorientadoresProjeto;
    private Aluno alunoSelecionado;
    private Educador coorientadorSelecionado;
    

    
    public ProjetoController() {
        this.alunoDao = new AlunoDao();
        this.educadorDao = new EducadorDao();
        this.projetoDao = new ProjetoDao();
        this.orientador = new Educador();
        this.aluno = new Aluno();
        this.projetos = ObservableCollections.
                observableList(new ArrayList<Projeto>());
        this.alunosProjeto = ObservableCollections.
                observableList(new ArrayList<Aluno>());
        this.coorientadoresProjeto = ObservableCollections.
                observableList(new ArrayList<Educador>());
        novo();
        pesquisar();
    }

    public List<Educador> getCoorientadores() {
        return coorientadores;
    }
    
     public void novo(){
        projetos.clear();
        alunosProjeto.clear();
        setProjetoDigitado(new Projeto());
    }
    
    public void pesquisar(){
        projetos.clear(); 
        alunosProjeto.clear();
        coorientadoresProjeto.clear();
        projetos.addAll(projetoDao.pesquisar(projetoDigitado));
        this.orientadores = educadorDao.pesquisar(orientador);
        this.coorientadores = orientadores;
        this.alunos = alunoDao.pesquisar(aluno);
    }
    
    public void salvar() throws Exception{
        projetoDigitado.setCoorientadores(coorientadoresProjeto);
        projetoDigitado.setAlunos(alunosProjeto);
        if(projetoDigitado.getId() == null)
            projetoDao.create(projetoDigitado);
        else
            projetoDao.edit(projetoDigitado);
        novo();
        pesquisar();
    }
    
    public void salvarAluno(Aluno alunoSelecionado){
        alunosProjeto.add(alunoSelecionado);
    }
    
    public void salvarCoorientadores(Educador coorientadorSelecionado){
        coorientadoresProjeto.add(coorientadorSelecionado);
    }
    
       
    public void excluir(){
        projetoDao.excluir(projetoDigitado);
        novo();
        pesquisar();
    }
    
       
    public Projeto getProjetoDigitado() {
        return projetoDigitado;
    }

    public void setProjetoDigitado(Projeto projetoDigitado) {
        Projeto oldProjetoDigitado = this.projetoDigitado;
        this.projetoDigitado = projetoDigitado;
        propertyChangeSupport.firePropertyChange("projetoDigitado", 
                oldProjetoDigitado,projetoDigitado);
    }

    public Projeto getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
        if(this.projetoSelecionado != null){
            setProjetoDigitado(projetoSelecionado);
            alunosProjeto.addAll(projetoDigitado.getAlunos());
            coorientadoresProjeto.addAll(projetoDigitado.getCoorientadores());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pl){
        propertyChangeSupport.addPropertyChangeListener(pl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pl){        
        propertyChangeSupport.removePropertyChangeListener(pl);
    }
    
    public List<Educador> getOrientadores() {
        return orientadores;
    }

    public void setOrientadores(List<Educador> educadores) {
        this.orientadores = educadores;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public List<Aluno> getAlunosProjeto() {
        return alunosProjeto;
    }

    public void setAlunosProjeto(List<Aluno> alunosProjeto) {
        this.alunosProjeto = alunosProjeto;
    }

    public void removerAluno() {
        this.alunosProjeto.remove(alunoSelecionado);
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }

    public List<Educador> getCoorientadoresProjeto() {
        return coorientadoresProjeto;
    }

    public void setCoorientadoresProjeto(List<Educador> coorientadoresProjeto) {
        this.coorientadoresProjeto = coorientadoresProjeto;
    }

    public void removerCoorientador() {
        this.coorientadoresProjeto.remove(coorientadorSelecionado);
    }

    public Educador getCoorientadorSelecionado() {
        return coorientadorSelecionado;
    }

    public void setCoorientadorSelecionado(Educador coorientadorSelecionado) {
        this.coorientadorSelecionado = coorientadorSelecionado;
    }
}
