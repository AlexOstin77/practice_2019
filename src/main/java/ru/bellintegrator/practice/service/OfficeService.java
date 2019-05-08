package ru.bellintegrator.practice.service;

import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;

import java.util.List;

/**
 * Service для работы с Office
 */
public interface OfficeService {

    /**
     * Получить отфильтрованный список офисов
     *
     * @param officeFilterView
     * @return List<OfficeFilterView>
     */
    List<OfficeFilterView> filterOfficeList(OfficeFilterView officeFilterView);

    /**
     * Получить офис по идентификатору
     *
     * @param id
     * @return Office
     */
    OfficeView getOfficeById(String id);

    /**
     * Обновить офис
     *
     * @param officeView
     */
    void updateOffice(OfficeView officeView);

    /**
     * Добавить новый офис
     *
     * @param officeView
     */
    void addOffice(OfficeView officeView);
}