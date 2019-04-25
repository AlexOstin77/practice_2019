package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.view.UserFiltrView;
import ru.bellintegrator.practice.view.UserView;

public interface UserController {

    /**
     * Выдает отфильтрованный список сотрудкиков
     * по параметрам
     * method:GET
     * @param userFilterView
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
     * @return JSON userFiltrView value
     * Out:
     * {
     *   “id”:””,
     *   “firstName”:””,
     *   “secondName”:””,
     *   “middleName”:””,
     *   “position”:””
     * }
     */
    Response filterUsers(@RequestBody UserFiltrView user);

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
    Response getUserById(@PathVariable("id") Integer id);

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
     * Out:
     * {
     *     “result”:”success”
     * }
     */
    Response updateUser(@RequestBody UserView user);

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
     * @return result
     * Out:
     * {
     *     “result”:”success”
     * }
     */
    Response saveUser(@RequestBody UserView user);

}
