package ru.bellintegrator.practice.dao.impl;

import com.google.common.base.Strings;
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
import javax.persistence.criteria.Predicate;
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
        return getListUsersByCriteria(officeId, firstName, secondName, middleName, possition, docCode, citizenshipCode);
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
        return getUniqueResultByCriteria("citizenship", code, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocType loadDocTypeByCodeAndName(String code, String name) {
        return getUniqueResultByCriteria("docType", code, name);
    }

    /**
     * Найти сотрудников по заданным параметрам
     * и вернуть их список
     *
     * @param officeId
     * @param firstName
     * @param secondName
     * @param middleName
     * @param possition
     * @param docCode
     * @param citizenshipCode
     * @return List<User>
     */
    private List<User> getListUsersByCriteria(String officeId, String firstName, String secondName, String middleName, String possition, String docCode, String citizenshipCode) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> itemRoot = criteriaQuery.from(User.class);
        Predicate criteria = criteriaBuilder.conjunction();
        Predicate predicateForOrgId = criteriaBuilder.equal(itemRoot.get("office"), Integer.valueOf(officeId));
        criteria = criteriaBuilder.and(criteria, predicateForOrgId);
        if (!(Strings.isNullOrEmpty(firstName))) {
            Predicate predicateForFirstName = criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("firstName")), "%" + firstName.toLowerCase() + "%");
            criteria = criteriaBuilder.and(criteria, predicateForFirstName);
        }
        if (!(Strings.isNullOrEmpty(secondName))) {
            Predicate predicateForSecondName = criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("secondName")), "%" + secondName.toLowerCase() + "%");
            criteria = criteriaBuilder.and(criteria, predicateForSecondName);
        }
        if (!(Strings.isNullOrEmpty(middleName))) {
            Predicate predicateForMiddleName = criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("secondName")), "%" + middleName.toLowerCase() + "%");
            criteria = criteriaBuilder.and(criteria, predicateForMiddleName);
        }
        if (!(Strings.isNullOrEmpty(possition))) {
            Predicate predicateForPossition = criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("possition")), "%" + possition + "%");
            criteria = criteriaBuilder.and(criteria, predicateForPossition);
        }
        if (!(Strings.isNullOrEmpty(docCode))) {
            Predicate predicateForDocCode = criteriaBuilder.equal(itemRoot.get("document").get("docType").get("code"), Integer.valueOf(docCode));
            criteria = criteriaBuilder.and(criteria, predicateForDocCode);
        }
        if (!(Strings.isNullOrEmpty(citizenshipCode))) {
            Predicate predicateForCitizenshipCodee = criteriaBuilder.equal(itemRoot.get("citizenship").get("code"), Integer.valueOf(citizenshipCode));
            criteria = criteriaBuilder.and(criteria, predicateForCitizenshipCodee);
        }
        criteriaQuery.where(criteria);
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Вернуть docType либо citizenshipCode по code, name
     * Тип возращаемого значения определяется параметром criteriaStr
     *
     * @param criteriaStr
     * @param code
     * @param name
     * @return T
     */
    private <T> T getUniqueResultByCriteria(String criteriaStr, String code, String name) {
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
