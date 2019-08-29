/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 *  @author LEANDRO
 */
@Entity
public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    private double nota;
    
    private String descricao;
    
    @OneToMany
    private List<Aluno> alunos;
    
    @ManyToOne
    private Educador orientador;
    
    @ManyToMany
    @JoinTable(name="projeto_coorientador",joinColumns=
            {@JoinColumn(name="projeto_id")},
            inverseJoinColumns={@JoinColumn(name="coorientador_id")})
    private List<Educador> coorientadores;
    
    
    public Projeto(){
        alunos = new ArrayList<Aluno>();
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    

    public List<Educador> getCoorientadores() {
        return coorientadores;
    }

    public void setCoorientadores(List coorientador) {
        this.coorientadores = (List<Educador>) coorientador;
    }

    
    public Educador getOrientador() {
        return orientador;
    }

    public void setOrientador(Educador orientador) {
        this.orientador =  orientador;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
     
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public String toString() {
        return descricao;
    }
    
}
