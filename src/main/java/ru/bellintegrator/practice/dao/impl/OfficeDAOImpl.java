package ru.bellintegrator.practice.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.dao.OfficeDAO;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.view.OfficeFilterView;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class OfficeDAOImpl implements OfficeDAO {

    private final EntityManager em;

    @Autowired
    public OfficeDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Office> filterOfficeList(OfficeFilterView officeFilterView) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Office> criteria = builder.createQuery(Office.class);
        Root<Office> Office = criteria.from(Office.class);
        if (officeFilterView.getName() != null && officeFilterView.getPhone() != null && officeFilterView.getActive() != null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.like(builder.lower(Office.get("name")), "%" + officeFilterView.getName().toLowerCase() + "%"), builder.like(Office.get("phone").as(String.class), "%" + String.valueOf(officeFilterView.getPhone()) + "%"), builder.equal(Office.get("isActive"), officeFilterView.getActive())));
        } else if (officeFilterView.getName() == null && officeFilterView.getPhone() != null && officeFilterView.getActive() != null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.like(Office.get("phone").as(String.class), "%" + String.valueOf(officeFilterView.getPhone()) + "%"), builder.equal(Office.get("isActive"), officeFilterView.getActive())));
        } else if (officeFilterView.getName() != null && officeFilterView.getPhone() == null && officeFilterView.getActive() != null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.like(builder.lower(Office.get("name")), "%" + officeFilterView.getName().toLowerCase() + "%"), builder.equal(Office.get("isActive"), officeFilterView.getActive())));
        } else if (officeFilterView.getName() != null && officeFilterView.getPhone() != null && officeFilterView.getActive() == null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.like(builder.lower(Office.get("name")), "%" + officeFilterView.getName().toLowerCase() + "%"), builder.like(Office.get("phone").as(String.class), "%" + String.valueOf(officeFilterView.getPhone()) + "%")));
        } else if (officeFilterView.getName() == null && officeFilterView.getPhone() == null && officeFilterView.getActive() != null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.equal(Office.get("isActive"), officeFilterView.getActive())));
        } else if (officeFilterView.getName() == null && officeFilterView.getPhone() != null && officeFilterView.getActive() == null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.like(Office.get("phone").as(String.class), "%" + String.valueOf(officeFilterView.getPhone()) + "%")));
        } else if (officeFilterView.getName() != null && officeFilterView.getPhone() == null && officeFilterView.getActive() == null) {
            criteria.where(builder.and(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()), builder.like(builder.lower(Office.get("name")), "%" + officeFilterView.getName().toLowerCase() + "%")));
        }  else if (officeFilterView.getName() == null && officeFilterView.getPhone() == null && officeFilterView.getActive() == null) {
            criteria.where(builder.equal(Office.get("organization").get("id"), officeFilterView.getOrgId()));
        }
        TypedQuery<Office> query = em.createQuery(criteria);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office loadOfficeById(Integer id) {
            return   em.find(Office.class, id);
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
        {
            return em.find(Organization.class, orgId);
        }
    }

}