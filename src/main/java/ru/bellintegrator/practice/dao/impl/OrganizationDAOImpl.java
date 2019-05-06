package ru.bellintegrator.practice.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.dao.OrganizationDAO;
import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.model.User;
import ru.bellintegrator.practice.view.OrganizationFilterView;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OrganizationDAOImpl implements OrganizationDAO {

    private final EntityManager em;

    @Autowired
    public OrganizationDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> filterOrganizationList(OrganizationFilterView organizationFilterView) {
        Session session = em.unwrap(Session.class);
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Organization.class);
        Conjunction objConjunction = Restrictions.conjunction();
        objConjunction.add(Restrictions.like("name", "%" + organizationFilterView.getName() + "%").ignoreCase());
        if (organizationFilterView.getInn() != null) {
            objConjunction.add(Restrictions.like("inn", "%" + organizationFilterView.getInn() + "%"));
        }
        if (organizationFilterView.getActive() != null) {
            objConjunction.add(Restrictions.like("isActive", organizationFilterView.getActive()));
        }
        criteria.add(objConjunction);
        List results = criteria.list();
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization loadOrganizationById(Integer id) {
        return em.find(Organization.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Organization organization) {
        em.persist(organization);
    }

}