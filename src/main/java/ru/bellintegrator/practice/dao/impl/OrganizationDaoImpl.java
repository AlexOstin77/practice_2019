package ru.bellintegrator.practice.dao.impl;

import com.google.common.base.Strings;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.practice.dao.OrganizationDao;
import ru.bellintegrator.practice.model.Organization;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class OrganizationDaoImpl implements OrganizationDao {

    private final EntityManager em;

    @Autowired
    public OrganizationDaoImpl(EntityManager em) {
        this.em = em;
    }

    private List addRestriction(String name, String inn, Boolean active) {
        Session session = em.unwrap(Session.class);
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Organization.class);
        Conjunction objConjunction = Restrictions.conjunction();
        objConjunction.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        if (!(Strings.isNullOrEmpty(inn))) {
            objConjunction.add(Restrictions.like("inn", inn, MatchMode.ANYWHERE));
        }
        if (active != null) {
            objConjunction.add(Restrictions.eq("isActive", active));
        }
        criteria.add(objConjunction);
        List results = criteria.list();
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> filterOrganizationList(String name, String inn, Boolean active) {
        return addRestriction(name, inn, active);
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
