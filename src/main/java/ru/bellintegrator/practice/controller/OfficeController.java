package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;
import java.util.List;

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
    List<OfficeFilterView> filterOffices(@RequestBody OfficeFilterView officeFilterView);

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
    OfficeView getOfficeById(@PathVariable("id") String id);

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
     */
    void updateOffice(@RequestBody OfficeView officeView);

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
     */
    void addOffice(@RequestBody OfficeView officeView);
}
