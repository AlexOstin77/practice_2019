package ru.bellintegrator.practice.dao.impl;

import com.google.common.base.Strings;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.dao.UserDao;
import ru.bellintegrator.practice.model.Country;
import ru.bellintegrator.practice.model.DocType;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.User;

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
public class UserDaoImpl implements UserDao {

    private final EntityManager em;

    @Autowired
    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> filterUserList(String officeId, String firstName, String secondName, String
            middleName, String possition, String docCode, String citizenshipCode) {
        String[] properties = ("office.id firstName secondName middleName possition code").split(" ");
        return addRestriction(properties, officeId, firstName, secondName, middleName, possition, docCode, citizenshipCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User loadUserById(Integer id) {
        return em.find(User.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office loadOfficeById(String id) {
        return em.find(Office.class, Integer.valueOf(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User User) {
        em.persist(User);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Country loadCitizenshipByCodeAndName(String code, String name) {
        return addRestriction("citizenship", code, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocType loadDocTypeByCodeAndName(String code, String name) {
        return addRestriction("docType", code, name);
    }

    private List addRestriction(String[] prop, String... args) {
        Session session = em.unwrap(Session.class);
        session.beginTransaction();
        Conjunction objConjunction = Restrictions.conjunction();
        Criteria userCriteria = session.createCriteria(User.class);
        Criteria docTypeCriteria = userCriteria.createCriteria("document").createCriteria("docType");
        Criteria citizenshipCriteria = userCriteria.createCriteria("citizenship");
        objConjunction.add(Restrictions.eq(prop[0], Integer.valueOf(args[0])));
        for (int i = 1; i < args.length - 2; i++) {
            if (!(Strings.isNullOrEmpty(args[i]))) {
                objConjunction.add(Restrictions.like(prop[i], args[i], MatchMode.ANYWHERE).ignoreCase());
            }
        }
        userCriteria.add(objConjunction);
        if (!(Strings.isNullOrEmpty(args[5]))) {
            docTypeCriteria.add(Restrictions.eq(prop[5], args[5]));
        }
        if (!(Strings.isNullOrEmpty(args[6]))) {
            citizenshipCriteria.add(Restrictions.eq(prop[5], args[6]));
        }
        List results = userCriteria.list();
        session.getTransaction().commit();
        return results;
    }


    private <T> T addRestriction(String criteriaStr, String code, String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<T> criteriaRoot = null;
        if (criteriaStr.equals("citizenship")) {
            criteria = builder.createQuery(Country.class);
            criteriaRoot = criteria.from(Country.class);
        }
        if (criteriaStr.equals("docType")) {
            criteria = builder.createQuery(DocType.class);
            criteriaRoot = criteria.from(DocType.class);
        }
        criteria.where(builder.equal(criteriaRoot.get("code"), code), builder.equal(criteriaRoot.get("name"), name));
        TypedQuery<T> query = em.createQuery(criteria);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
