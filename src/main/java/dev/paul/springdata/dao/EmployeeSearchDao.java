package dev.paul.springdata.dao;

import dev.paul.springdata.models.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchDao {

    private final EntityManager em;

    public List<Employee> findAllBySimpleQuery(String firstname, String lastname, String email) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        // SELECT * FROM Employee
        Root<Employee> root = criteriaQuery.from(Employee.class);

        // Prepare WHERE clause
        // WHERE firstname LIKE '%paul%'
        Predicate firstnamePredicate = criteriaBuilder
                .like(root.get("firstname"),"%" + firstname + "%");
        // WHERE lastname LIKE '%ryan%'
        Predicate lastnamePredicate = criteriaBuilder
                .like(root.get("lastname"),"%" + lastname + "%");
        // WHERE email LIKE '%paul@gmail.com%'
        Predicate emailPredicate = criteriaBuilder
                .like(root.get("email"),"%" + email + "%");

        Predicate orPredicate = criteriaBuilder.or(
                firstnamePredicate,
                lastnamePredicate,
                emailPredicate);

        /***
         * final query build from orPredicate
         * SELECT * FROM employee WHERE firstname LIKE '%paul%' OR lastname LIKE '%ryan% OR email LIKE '%email@gmail.com%';
         */
        // criteriaQuery.where(orPredicate);

        /***
         * if we want to have some query like
         * SELECT * FROM employee WHERE firstname LIKE '%paul%' OR lastname LIKE '%ryan% AND email LIKE '%email@gmail.com%'
         * We can create it as below
         */
        Predicate firstnameOrLastnamePredicate = criteriaBuilder.or(
                firstnamePredicate,
                lastnamePredicate);
        // Predicate andEmailPredicate = criteriaBuilder.and(firstnameOrLastnamePredicate, emailPredicate);
        // criteriaQuery.where(andEmailPredicate);
        criteriaQuery.where(criteriaBuilder.and(firstnameOrLastnamePredicate), emailPredicate);

        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Employee> findAllByCriteria(SearchRequest request) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Employee> root = criteriaQuery.from(Employee.class);
        if (request.getFirstname() != null) {
            Predicate firstnamePredicate = criteriaBuilder
                    .like(root.get("firstname"), "%" + request.getFirstname() + "%");
        }

        if (request.getLastname() != null) {
            Predicate lastnamePredicate = criteriaBuilder
                    .like(root.get("lastname"), "%" + request.getLastname() + "%");
        }

        if (request.getEmail() != null) {
            Predicate emailPredicate = criteriaBuilder
                    .like(root.get("email"), "%" + request.getEmail() + "%");
        }

        criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
        TypedQuery<Employee> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}



