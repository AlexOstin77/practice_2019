package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.List;

public interface OrganizationController {

    /**
     * Выдает отфильтрованный список организаций
     * по параметрам
     * method:GET
     *
     * @param organizationFilterView In (фильтр):
     *                               {
     *                               “name”:””, //обязательный параметр
     *                               “inn”:””,
     *                               “isActive”:””
     *                               }
     * @return JSON organizationFilterView value
     * Out:
     * [
     * {
     * “id”:””,
     * “name”:””,
     * “isActive”:”true”
     * },..
     * ]
     */
    List<OrganizationFilterView> filterOrganizations(@RequestBody OrganizationFilterView organizationFilterView);

    /**
     * Поиск организации по id
     * method:GET
     *
     * @param id In
     *           {id}
     * @return JSON organizationFilterView value
     * Out:
     * {
     * “id”:””,
     * “name”:””,
     * “fullName”:””,
     * “inn”:””,
     * “kpp”:””,
     * “address”:””,
     * “phone”,””,
     * “isActive”:”true”
     * }
     */
    OrganizationView getOrganizationById(@PathVariable("id") String id);

    /**
     * Обновление значений организации
     * method:POST
     *
     * @param organizationView In:
     *                         {
     *                         “id”:””, //обязательный параметр
     *                         “name”:””, //обязательный параметр
     *                         “fullName”:””, //обязательный параметр
     *                         “inn”:””, //обязательный параметр
     *                         “kpp”:””,  //обязательный параметр
     *                         “address”:””, //обязательный параметр
     *                         “phone”,””,
     *                         “isActive”:”true”
     *                         }
     */
    void updateOrganizaton(@RequestBody OrganizationView organizationView);

    /**
     * Добавление организации
     * method:POST
     *
     * @param organizationView In:
     *                         {
     *                         “name”:””, //обязательный параметр
     *                         “fullName”:””, //обязательный параметр
     *                         “inn”:””, //обязательный параметр
     *                         “kpp”:””, //обязательный параметр
     *                         “address”:””, //обязательный параметр
     *                         “phone”,””,
     *                         “isActive”:”true”
     *                         }
     */
    void addOrganization(@RequestBody OrganizationView organizationView);

}
