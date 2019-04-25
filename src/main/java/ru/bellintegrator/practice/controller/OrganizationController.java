package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

public interface OrganizationController {

    /**
     * Выдает отфильтрованный список организаций
     * по параметрам
     * method:GET
     * @param organizationFilterView
     * In (фильтр):
     * {
     *   “name”:””, //обязательный параметр
     *   “inn”:””,
     *   “isActive”:””
     * }
     * @return JSON organizationFiltrView value
     * Out:
     * [
     *   {
     *     “id”:””,
     *     “name”:””,
     *     “isActive”:”true”
     *   },..
     *   ]
     */
    Response filterOrganizations(@RequestBody OrganizationFilterView organizationFilterView);

    /**
     * Поиск организации по id
     * method:GET
     * @param id
     * In
     * {id}
     * @return JSON organizationFiltrView value
     * Out:
     * {
     *   “id”:””,
     *   “name”:””,
     *   “fullName”:””,
     *   “inn”:””,
     *   “kpp”:””,
     *   “address”:””,
     *   “phone”,””,
     *   “isActive”:”true”
     * }
     */
    Response getOrganizationById(@PathVariable("id") String id) ;

    /**
     * Обновление значений организации
     * method:POST
     * @param organizationView
     * In:
     * {
     *   “id”:””, //обязательный параметр
     *   “name”:””, //обязательный параметр
     *   “fullName”:””, //обязательный параметр
     *   “inn”:””, //обязательный параметр
     *   “kpp”:””,  //обязательный параметр
     *   “address”:””, //обязательный параметр
     *   “phone”,””,
     *   “isActive”:”true”
     * }
     * @return result
     * Out:
     * {
     *     “result”:”success”
     * }
     */
    Response updateOrganizaton(@RequestBody OrganizationView organizationView);

    /**
     * Добавление организации
     * method:POST
     * @param organizationView
     * In:
     * {
     *   “name”:””, //обязательный параметр
     *   “fullName”:””, //обязательный параметр
     *   “inn”:””, //обязательный параметр
     *   “kpp”:””, //обязательный параметр
     *   “address”:””, //обязательный параметр
     *   “phone”,””,
     *   “isActive”:”true”
     * }
     * @return result
     *      * Out:
     *      * {
     *      *     “result”:”success”
     *      * }
     */
    Response addOrganization(@RequestBody OrganizationView organizationView);

}
