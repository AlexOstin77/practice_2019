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
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.service.OfficeService;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OfficeServiceImpl implements OfficeService {
    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeDAO dao;

    @Autowired
    public OfficeServiceImpl(OfficeDAO dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfficeFilterView> filterOfficeList(OfficeFilterView officeFilterView) {
        if (Strings.isNullOrEmpty(officeFilterView.getOrgId())) {
            throw new CustomException("Не заполнено обязательное поле OrgId*");
        }

        if (!officeFilterView.getOrgId().matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID офиса %s", officeFilterView.getOrgId()));
        }
        log.info("filtrOfficeView {} " + officeFilterView.toString());
        List<Office> all = dao.filterOfficeList(officeFilterView);
        List<OfficeFilterView> officesView = new ArrayList<>();
        Function<Office, OfficeFilterView> mapOffice = p -> {
            OfficeFilterView view = new OfficeFilterView(String.valueOf(p.getId()), p.getName(), p.getActive());
            log.info("view {}" + view.toString());
            return view;
        };
        return all.stream().map(mapOffice).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public OfficeView getOfficeById(String id) {

        log.info("getId {} " + id);
        if (id == null) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID офиса %s", id));
        }
        Office office = dao.loadOfficeById(Integer.valueOf(id));
        if (office == null) {
            return new OfficeView();
        }
        OfficeView officeView = new OfficeView();
        officeView.setId(Integer.toString(office.getId()));
        officeView.setName(office.getName());
        officeView.setPhone(office.getPhone());
        officeView.setAddress(office.getAddress());
        officeView.setActive(office.getActive());
        log.info("office {} " + officeView);
        return officeView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateOffice(OfficeView view) {
        log.info("view {} " + view);
        if (Strings.isNullOrEmpty(view.getId()) || Strings.isNullOrEmpty(view.getName())
                || Strings.isNullOrEmpty(view.getAddress()) || view.getActive() == null
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }
        Office office = dao.loadOfficeById(Integer.valueOf(view.getId()));
        if (office == null) {
            throw new CustomException(String.format("Не найден офис с ID %s", view.getId()));
        }
        office.setName(view.getName());
        office.setPhone(view.getPhone());
        office.setAddress(view.getAddress());
        if (view.getActive() != null) {
            office.setActive(view.getActive());
        }
        log.info("office {} " + office.toString());
        dao.save(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addOffice(OfficeView view) {
        log.info("office {} " + view);
        if (Strings.isNullOrEmpty(view.getOrgId())) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        Office office = new Office();
        if (!view.getOrgId().matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID организации %s", Integer.valueOf(view.getOrgId())));
        }
        Organization organization = dao.loadOrgById(Integer.valueOf(view.getOrgId()));
        if (organization == null) {
            throw new CustomException(String.format("Неверное ID организации %s", Integer.valueOf(view.getOrgId())));
        }
        office.setName(view.getName());
        office.setAddress(view.getAddress());
        office.setPhone(view.getPhone());
        if (view.getActive() != null) {
            office.setActive(view.getActive());
        }
        office.setOrganization(organization);
        log.info("office {} " + office.toString());
        dao.save(office);
    }

}
