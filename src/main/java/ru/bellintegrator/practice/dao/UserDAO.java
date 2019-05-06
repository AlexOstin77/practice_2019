package ru.bellintegrator.practice.dao;

import ru.bellintegrator.practice.model.Country;
import ru.bellintegrator.practice.model.DocType;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.User;
import ru.bellintegrator.practice.view.UserFiltrView;
import java.util.List;

/**
 * DAO для работы с User
 */
public interface UserDAO {

    /**
     * Получить отфильтрованный список сотрудников
     *
     * @param userFiltrView
     * @return List<User>
     */
    List<User> filterUserList(UserFiltrView userFiltrView);

    /**
     * Получить сотрудника по идентификатору
     *
     * @param id
     * @return User
     */
    User loadUserById(Integer id);

    /**
     * Сохранить сотрудника
     *
     * @param user
     */
    void save(User user);

    /**
     * Получить офис по идентификатору
     *
     * @param id
     * @return Office
     */
    Office loadOfficeById(String id);

    /**
     * Получить страну по коду и наименованию
     *
     * @param code, name
     * @return Counntry
     */
    Country loadCitizenshipByCodeAndName(String code, String name);

    /**
     * Получить тип документа по коду и наименованию
     *
     * @param code, name
     * @return DocType
     */
    DocType loadDocTypeByCodeAndName(String code, String name);

}
