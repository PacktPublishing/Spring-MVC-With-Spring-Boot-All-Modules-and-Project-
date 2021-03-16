package com.company.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.company.model.Student;

@Repository
public class StudentCustomRepository {
	
	@PersistenceContext
    private EntityManager entityManager;
	
    public List<Student> findStudentsByNames(Set<String> names) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> student = query.from(Student.class);
 
        Path<String> namePath = student.get("name");
 
        List<Predicate> predicates = new ArrayList<>();
        for (String name : names) {
            predicates.add(cb.like(namePath, name));
        }
        
        query.select(student)
            .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
 
        return entityManager.createQuery(query)
            .getResultList();
    }
}
