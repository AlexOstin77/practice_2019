package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;

public interface OfficeController {

    /**
     * Выдает отфильтрованный список организаций
     * по параметрам
     * method:GET
     * @param officeFilterView
     * In (фильтр):
     * {
     *   “orgId”:””, //обязательный параметр
     *   “name”:””,
     *   “phone”:””,
     *   “isActive”
     * }
     * @return JSON officeFiltrView value
     * Out:
     * [
     *   {
     *     “id”:””,
     *     “name”:””,
     *     “isActive”:”true”
     *   },
     *   ...
     * ]
     */
    Response filterOffices(@RequestBody OfficeFilterView office);

    /**
     * Поиск офиса по id
     * @param id
     * @return JSON officeView value
     * method:GET
     * Out:
     * {
     *   “id”:””,
     *   “name”:””,
     *   “address”:””,
     *   “phone”,””,
     *   “isActive”:”true”
     * }
     */
    Response getOfficeById(@PathVariable("id") String id);

    /**
     * Обновление значений офиса
     * method:POST
     * @param officeView
     * In:
     * {
     *   “id”:””, //обязательный параметр
     *   “name”:””, //обязательный параметр
     *   “address”:””, //обязательный параметр
     *   “phone”,””,
     *   “isActive”:”true” //пример
     * }
     * @return result
     * Out:
     * {
     *     “result”:”success”
     * }
     */
    Response updateOffice(@RequestBody OfficeView office);

    /**
     * Добавление офиса
     * method:POST
     * @param officeView
     * In:
     * {
     *   “orgId”:””, //обязательный параметр
     *   “name”:””,
     *   “address”:””,
     *   “phone”,””,
     *   “isActive”:”true”
     * }
     * @return result
     * Out:
     * {
     *     “result”:”success”
     * }
     */
    Response addOffice(@RequestBody OfficeView office);
}
