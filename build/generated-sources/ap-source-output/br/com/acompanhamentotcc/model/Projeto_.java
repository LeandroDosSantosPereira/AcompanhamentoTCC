package br.com.acompanhamentotcc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Projeto.class)
public abstract class Projeto_ {

	public static volatile ListAttribute<Projeto, Educador> coorientadores;
	public static volatile ListAttribute<Projeto, Aluno> alunos;
	public static volatile SingularAttribute<Projeto, Long> id;
	public static volatile SingularAttribute<Projeto, Educador> orientador;
	public static volatile SingularAttribute<Projeto, Double> nota;
	public static volatile SingularAttribute<Projeto, String> status;
	public static volatile SingularAttribute<Projeto, String> descricao;

}

