package ru.bellintegrator.practice.dao.impl;

import com.google.common.base.Strings;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.dao.UserDAO;
import ru.bellintegrator.practice.model.*;
import ru.bellintegrator.practice.view.UserFiltrView;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private final EntityManager em;
    @Autowired
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> filterUserList(UserFiltrView userFiltrView) {
        Session session = em.unwrap(Session.class);
        session.beginTransaction();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Criteria userCriteria = session.createCriteria(User.class);
        Criteria citezenshipCriteria = userCriteria.createCriteria("citizenship");
        Criteria docTypeCriteria = userCriteria.createCriteria("document").createCriteria("docType");
        Conjunction objConjunction = Restrictions.conjunction();
        objConjunction.add(Restrictions.eq("office.id", Integer.valueOf(userFiltrView.getOfficeId())));
        if (!(Strings.isNullOrEmpty(userFiltrView.getFirstName()))) {
            objConjunction.add(Restrictions.like("firstName", userFiltrView.getFirstName(), MatchMode.ANYWHERE ).ignoreCase());
        }
        if (!(Strings.isNullOrEmpty(userFiltrView.getSecondName()))) {
            objConjunction.add(Restrictions.like("secondName", userFiltrView.getSecondName(), MatchMode.ANYWHERE).ignoreCase());
        }
        if (!(Strings.isNullOrEmpty(userFiltrView.getMiddleName()))) {
            objConjunction.add(Restrictions.like("middleName", userFiltrView.getMiddleName(), MatchMode.ANYWHERE ).ignoreCase());
        }
        if (!(Strings.isNullOrEmpty(userFiltrView.getPossition()))) {
            objConjunction.add(Restrictions.like("possition", userFiltrView.getPossition(), MatchMode.ANYWHERE).ignoreCase());
        }
        if (!(Strings.isNullOrEmpty(userFiltrView.getCitizenShipCode()))) {
            citezenshipCriteria.add(Restrictions.eq("code", userFiltrView.getCitizenShipCode()));
        }
        if (!(Strings.isNullOrEmpty(userFiltrView.getDocCode()))) {
            docTypeCriteria.add(Restrictions.eq("code", userFiltrView.getDocCode()));
        }
        userCriteria.add(objConjunction);
        List results = userCriteria.list();
        session.getTransaction().commit();
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User loadUserById(Integer id) {
            return   em.find(User.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office loadOfficeById(String id) {
        return   em.find(Office.class, Integer.valueOf(id));
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
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Country> criteria = builder.createQuery(Country.class);
        Root<Country> citizenship = criteria.from(Country.class);
        criteria.where(builder.equal(citizenship.get("code"), code), builder.equal(citizenship.get("name"), name));
        TypedQuery<Country> query = em.createQuery(criteria);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocType loadDocTypeByCodeAndName(String code, String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DocType> criteria = builder.createQuery(DocType.class);
        Root<DocType> docType = criteria.from(DocType.class);
        criteria.where(builder.equal(docType.get("code"), code), builder.equal(docType.get("name"), name));
        TypedQuery<DocType> query = em.createQuery(criteria);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}