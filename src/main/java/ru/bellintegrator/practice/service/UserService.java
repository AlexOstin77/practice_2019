package ru.bellintegrator.practice.service;

import ru.bellintegrator.practice.model.User;
import ru.bellintegrator.practice.view.UserFiltrView;
import ru.bellintegrator.practice.view.UserView;

import java.util.List;

/**
 * Service для работы с User
 */
public interface UserService {

    /**
     * Получить отфильтрованный список сотрудников
     *
     * @param userFiltrView
     * @return List<UserFiltrView>
     */
    List<UserFiltrView> filterUserList(UserFiltrView userFiltrView);

    /**
     * Получить сотрудника по идентификатору
     *
     * @param id
     * @return UserView
     */
    UserView getUserById(String id);

    /**
     * Обновить сотрудника сотрудника
     *
     * @param userView
     */
    void updateUser(UserView userView);

    /**
     * Добавить нового сотрудника
     *
     * @param userView
     */
    void addUser(UserView userView);

    /**
     * Сохранить сотрудника
     *
     * @param user, userView
     */
    void saveUser(UserView userView, User user);

}