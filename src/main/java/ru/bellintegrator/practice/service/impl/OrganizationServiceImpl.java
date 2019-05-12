package ru.bellintegrator.practice.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.OfficeDao;
import ru.bellintegrator.practice.dao.OrganizationDao;
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.service.OrganizationService;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationDao dao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationFilterView> filterOrganizationList(OrganizationFilterView organizationFilterView) {
        log.info("view {} " + organizationFilterView.toString());
        validateFilter(organizationFilterView.getName(), organizationFilterView.getInn());
        List<Organization> all = dao.filterOrganizationList(organizationFilterView.getName(), organizationFilterView.getInn(), organizationFilterView.getActive());
        Function<Organization, OrganizationFilterView> mapOrganization = p -> {
            OrganizationFilterView view = new OrganizationFilterView();
            view.setId(String.valueOf(p.getId()));
            view.setName(p.getName());
            view.setActive(p.getActive());
            log.info(view.toString());
            return view;
        };
        return all.stream().map(mapOrganization).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OrganizationView getOrganizationById(String id) {
        log.info("getId {} " + id);
        validateId(id);
        Organization organization = dao.loadOrganizationById(Integer.valueOf(id));
        return setResponseToView(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateOrganization(OrganizationView view) {
        log.info("view {} " + view);
        validateViewUpdate(view);
        Organization organization = dao.loadOrganizationById(Integer.valueOf(view.getId()));
        organization = setResponseToModel(view, organization);
        dao.save(organization);
        log.info("organization {}" + organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(OrganizationView view) {
        log.info("view {} " + view);
        validateViewSave(view);
        Organization organization = new Organization();
        organization = setResponseToModel(view, organization);
        dao.save(organization);
        log.info(" organization {} " + organization);
    }

    /**
     * Проверить view фильтра организации на заполнение
     * обязательных полей
     * в случае ошибки вызвать собственное исключения
     *
     * @param name
     * @param inn
     */
    private void validateFilter(String name, String inn) {
        if (Strings.isNullOrEmpty(name)) {
            throw new CustomException("Не заполнено обязательное поле name*");
        }
        if (!(Strings.isNullOrEmpty(inn)) && !(inn.matches("^\\d{0,10}$"))) {
            throw new CustomException(String.format("Неверное ИНН организации %s", inn));
        }
    }

    /**
     * Проверить идентификатор
     * на числовой тип данных
     * в случае ошибки вызвать собственное исключения
     *
     * @param id
     */
    private void validateId(String id) {
        if (id == null) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID организации %s", id));
        }
    }

    /**
     * Проверить view обновления организации на заполнение
     * обязательных полей
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     */
    private void validateViewUpdate(OrganizationView view) {
        if (Strings.isNullOrEmpty(view.getId()) || Strings.isNullOrEmpty(view.getName())
                || Strings.isNullOrEmpty(view.getFullName()) || Strings.isNullOrEmpty(view.getInn())
                || Strings.isNullOrEmpty(view.getKpp()) || Strings.isNullOrEmpty(view.getAddress())
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }

    }

    /**
     * Проверить view добавления организации на заполнение
     * обязательных полей
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     */
    private void validateViewSave(OrganizationView view) {
        if (Strings.isNullOrEmpty(view.getName()) || Strings.isNullOrEmpty(view.getFullName()) || Strings.isNullOrEmpty(view.getInn())
                || Strings.isNullOrEmpty(view.getKpp()) || Strings.isNullOrEmpty(view.getAddress())
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }
    }

    /**
     * Проверить organization на null
     * в случае успеха записать в модель
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     * @param organization
     * @return Organization
     */
    private Organization setResponseToModel(OrganizationView view, Organization organization) {
        if (organization == null) {
            throw new CustomException(String.format("Не найдена организация с ID %s", view.getId()));
        }
        organization.setName(view.getName());
        organization.setFullName(view.getFullName());
        organization.setInn(view.getInn());
        organization.setKpp(view.getKpp());
        organization.setAddress(view.getAddress());
        organization.setPhone(view.getPhone());
        if (view.getActive() != null) {
            organization.setActive(view.getActive());
        }
        return organization;
    }

    /**
     * Проверить view на null
     * в случае успеха записать в view
     * в случае ошибки вызвать собственное исключения
     *
     * @param organization
     * @return OrganizationView
     */
    private OrganizationView setResponseToView(Organization organization) {
        OrganizationView view = new OrganizationView();
        if (organization == null) {
            return view;
        }
        view.setName(organization.getName());
        view.setFullName(organization.getFullName());
        view.setInn(organization.getInn());
        view.setKpp(organization.getKpp());
        view.setAddress(organization.getAddress());
        view.setPhone(organization.getPhone());
        if (view.getActive() != null) {
            organization.setActive(view.getActive());
        }
        return view;
    }

}
