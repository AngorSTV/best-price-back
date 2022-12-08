package ru.angorstv.bestprice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.angorstv.bestprice.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {

    @PersistenceContext
    private final EntityManager em;

    public CustomProductRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<Product> findByNamePaging(String term, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Product.class)));
        long count = em.createQuery(countQuery).getSingleResult();

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> from = criteriaQuery.from(Product.class);
        CriteriaQuery<Product> select = criteriaQuery.select(from);

        select.where(criteriaBuilder.like(from.get("name"), "%" + term + "%"));
        TypedQuery<Product> query = em.createQuery(select);

        query.setFirstResult(pageable.getPageNumber() - 1);
        query.setMaxResults(pageable.getPageSize());


        return null;
    }
}
