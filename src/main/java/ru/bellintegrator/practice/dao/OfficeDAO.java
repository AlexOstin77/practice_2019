package ru.bellintegrator.practice.dao;

import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.view.OfficeFilterView;

import java.util.List;

/**
 * DAO для работы с Office
 */
public interface OfficeDao {

    /**
     * Получить отфильтрованный список офисов
     *
     * @param officeFilterView
     * @return List<Office>
     */
    List<Office> filterOfficeList(String orgId, String name, String phone, Boolean active);

    /**
     * Получить офис по идентификатору
     *
     * @param id
     * @return Office
     */
    Office loadOfficeById(Integer id);

    /**
     * Сохранить офис
     *
     * @param office
     */
    void save(Office office);

    /**
     * Получить организацию по идентификатору
     *
     * @param orgId
     * @return Organization
     */
    Organization loadOrgById(Integer orgId);


}
