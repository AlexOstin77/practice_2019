package ru.bellintegrator.practice.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.OfficeDAO;
import ru.bellintegrator.practice.dao.OrganizationDAO;
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
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OrganizationServiceImpl implements OrganizationService {
    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationDAO dao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDAO dao, OfficeDAO daoOffice) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrganizationFilterView> filterOrganizationList(OrganizationFilterView organizationFilterView) {
        log.info("view {} " + organizationFilterView.toString());
        if (Strings.isNullOrEmpty(organizationFilterView.getName())) {
            throw new CustomException("Не заполнено обязательное поле name*");
        }
        if (!(Strings.isNullOrEmpty(organizationFilterView.getInn())) && !(organizationFilterView.getInn().matches("^\\d{0,10}$"))) {
            throw new CustomException(String.format("Неверное ИНН организации %s", organizationFilterView.getInn()));
        }
        List<Organization> all = dao.filterOrganizationList(organizationFilterView);
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
        if (id == null) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID организации %s", id));
        }
        Organization organization = dao.loadOrganizationById(Integer.valueOf(id));
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateOrganization(OrganizationView view) {
        if (Strings.isNullOrEmpty(view.getId()) || Strings.isNullOrEmpty(view.getName())
                || Strings.isNullOrEmpty(view.getFullName()) || Strings.isNullOrEmpty(view.getInn())
                || Strings.isNullOrEmpty(view.getKpp()) || Strings.isNullOrEmpty(view.getAddress())
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }
        Organization organization = dao.loadOrganizationById(Integer.valueOf(view.getId()));
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
        log.info("view {} " + view);
        dao.save(organization);
        log.info(" organization " + organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void add(OrganizationView view) {
        log.info("view {} " + view);
        if (Strings.isNullOrEmpty(view.getName()) || Strings.isNullOrEmpty(view.getFullName()) || Strings.isNullOrEmpty(view.getInn())
                || Strings.isNullOrEmpty(view.getKpp()) || Strings.isNullOrEmpty(view.getAddress())
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }
        Organization organization = new Organization();
        organization.setName(view.getName());
        organization.setFullName(view.getFullName());
        organization.setInn(view.getInn());
        organization.setKpp(view.getKpp());
        organization.setAddress(view.getAddress());
        organization.setPhone(view.getPhone());
        if (view.getActive() != null) {
            organization.setActive(view.getActive());
        }
        dao.save(organization);
        log.info(" organization {} " + organization);
    }

}
