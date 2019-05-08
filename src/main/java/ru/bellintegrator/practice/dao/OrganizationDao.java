package ru.bellintegrator.practice.dao;

import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.view.OrganizationFilterView;

import java.util.List;

/**
 * DAO для работы с Organization
 */
public interface OrganizationDao {

    /**
     * Получить отфильтрованныйсписок организаций
     *
     * @param name, inn, active
     * @return List<Organization>
     */
    List<Organization> filterOrganizationList(String name, String inn, Boolean active);

    /**
     * Получить организацию по идентификатору
     *
     * @param id
     * @return Organization
     */
    Organization loadOrganizationById(Integer id);

    /**
     * Сохранить организацию
     *
     * @param organization
     */
    void save(Organization organization);


}
