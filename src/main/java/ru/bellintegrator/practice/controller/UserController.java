package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.view.UserFiltrView;
import ru.bellintegrator.practice.view.UserView;
import java.util.List;

public interface UserController {

    /**
     * Выдает отфильтрованный список сотрудкиков
     * по параметрам
     * method:GET
     * @param userFiltrView
     * In (фильтр):
     * {
     *   “officeId”:””, //обязательный параметр
     *   “firstName”:””,
     *   “lastName”:””,
     *   “middleName”:””,
     *   “position”,””,
     *   “docCode”:””,
     *   “citizenshipCode”:””
     * }
     */
    List<UserFiltrView> filterUsers(@RequestBody UserFiltrView userFiltrView);

    /**
     * Поиск сотрудника по id
     * method:GET
     * @param id
     * In
     * {id}
     * @return JSON userFiltrView value
     * Out:
     * {
     *   “id”:””,
     *   “firstName”:””,
     *   “secondName”:””,
     *   “middleName”:””,
     *   “position”:””
     *   “phone”,””,
     *   “docName”:””,
     *   “docNumber”:””,
     *   “docDate”:””,
     *   “citizenshipName”:””,
     *   “citizenshipCode”:””,
     *   “isIdentified”:”true”
     * }
     */
    UserView getUserById(@PathVariable("id") String id);

    /**
     * Обновление значениz сотрудника
     * method:POST
     * @param userView
     * {
     *   “id”:””, //обязательный параметр
     *   “officeId”:””,
     *   “firstName”:””, //обязательный параметр
     *   “secondName”:””,
     *   “middleName”:””,
     *   “position”:”” //обязательный параметр
     *   “phone”,””,
     *   “docName”:””,
     *   “docNumber”:””,
     *   “docDate”:””,
     *   “citizenshipCode”:””,
     *   “isIdentified”:”true” //пример
     * @return result
     */
    void updateUser(@RequestBody UserView userView);

    /**
     * Добавление сотрудика
     * method:POST
     * @param userView
     * In:
     * {
     *   “officeId”:””, //обязательный параметр
     *   “firstName”:””, //обязательный параметр
     *   “secondName”:””,
     *   “middleName”:””,
     *   “position”:”” //обязательный параметр
     *   “phone”,””,
     *   “docCode”:””,
     *   “docName”:””,
     *   “docNumber”:””,
     *   “docDate”:””,
     *   “citizenshipCode”:””,
     *   “isIdentified”:”true” //пример
     * }
     */
    void addUser(@RequestBody UserView userView);

}
