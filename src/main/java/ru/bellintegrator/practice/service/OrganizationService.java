package ru.bellintegrator.practice.service;

import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.List;

/**
 * Service для работы с Organization
 */
public interface OrganizationService {

    /**
     * Получить отфильтрованныйсписок организаций
     *
     * @param organizationFilterView
     * @return List<OrganizationFilterView>
     */
    List<OrganizationFilterView> filterOrganizationList(OrganizationFilterView organizationFilterView);

    /**
     * Получить организацию по идентификатору
     *
     * @param id
     * @return OrganizationView
     */
    OrganizationView getOrganizationById(String id);

    /**
     * Сохранить организацию
     *
     * @param organizationView
     */
    void updateOrganization(OrganizationView organizationView);

    /**
     * Добавить новую организацию
     *
     * @param organizationView
     */

    void add(OrganizationView organizationView);

}