package br.com.acompanhamentotcc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Aluno.class)
public abstract class Aluno_ {

	public static volatile SingularAttribute<Aluno, Integer> idade;
	public static volatile SingularAttribute<Aluno, Curso> cursos;
	public static volatile SingularAttribute<Aluno, String> nome;
	public static volatile SingularAttribute<Aluno, Long> id;
	public static volatile SingularAttribute<Aluno, Integer> ra;

}

