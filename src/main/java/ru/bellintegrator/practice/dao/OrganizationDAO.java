package ru.bellintegrator.practice.dao;

import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import java.util.List;

/**
 * DAO для работы с Organization
 */
public interface OrganizationDAO {

    /**
     * Получить отфильтрованныйсписок организаций
     *
     * @param organizationFilterView
     * @return List<Organization>
     */
    List<Organization> filterOrganizationList(OrganizationFilterView organizationFilterView);

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
