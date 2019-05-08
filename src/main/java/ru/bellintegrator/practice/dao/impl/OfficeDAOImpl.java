package ru.bellintegrator.practice.dao.impl;

import com.google.common.base.Strings;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.dao.OfficeDao;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.Organization;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class OfficeDaoImpl implements OfficeDao {

    private final EntityManager em;

    @Autowired
    public OfficeDaoImpl(EntityManager em) {
        this.em = em;
    }

    private List addRestriction(String organization, String name, String phone, Boolean active) {
        Session session = em.unwrap(Session.class);
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Office.class);
        Conjunction objConjunction = Restrictions.conjunction();
        objConjunction.add(Restrictions.eq("organization.id", Integer.valueOf(organization)));
        if (!(Strings.isNullOrEmpty(name))) {
            objConjunction.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        if (!(Strings.isNullOrEmpty(phone))) {
            objConjunction.add(Restrictions.like("phone", phone, MatchMode.ANYWHERE));
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
    public List<Office> filterOfficeList(String orgId, String name, String phone, Boolean active) {
        return addRestriction(orgId, name, phone, active);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office loadOfficeById(Integer id) {
        return em.find(Office.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Office Office) {
        em.persist(Office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization loadOrgById(Integer orgId) {
        return em.find(Organization.class, orgId);
    }

}