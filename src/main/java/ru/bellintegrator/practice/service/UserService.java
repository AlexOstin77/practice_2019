package ru.bellintegrator.practice.service;

import ru.bellintegrator.practice.model.User;
import ru.bellintegrator.practice.view.UserFilterView;
import ru.bellintegrator.practice.view.UserView;

import java.util.List;

/**
 * Service для работы с User
 */
public interface UserService {

    /**
     * Получить отфильтрованный список сотрудников
     *
     * @param userFilterView
     * @return List<UserFilterView>
     */
    List<UserFilterView> filterUserList(UserFilterView userFilterView);

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