package ru.bellintegrator.practice.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.OfficeDao;
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.Organization;
import ru.bellintegrator.practice.service.OfficeService;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class OfficeServiceImpl implements OfficeService {
    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeDao dao;

    @Autowired
    public OfficeServiceImpl(OfficeDao dao) {
        this.dao = dao;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfficeFilterView> filterOfficeList(OfficeFilterView officeFilterView) {
        validateFilterView(officeFilterView);
        log.debug("filtrOfficeView {} ", officeFilterView.toString());
        List<Office> all = dao.filterOfficeList(officeFilterView.getOrgId(), officeFilterView.getName(), officeFilterView.getPhone(), officeFilterView.getActive());
        Function<Office, OfficeFilterView> mapOffice = p -> {
            OfficeFilterView view = new OfficeFilterView(String.valueOf(p.getId()), p.getName(), p.getActive());
            log.debug("view {} ", view.toString());
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
        log.debug("getId {} ", id);
        validateViewId(id);
        Office office = dao.loadOfficeById(Integer.valueOf(id));
        return setResponseToView(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateOffice(OfficeView view) {
        log.debug("view {} ", view);
        validateUpdateView(view);
        Office office = dao.loadOfficeById(Integer.valueOf(view.getId()));
        setResponseToModel(view, office);
        dao.save(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addOffice(OfficeView view) {
        log.debug("office {} ", view);
        Office office = new Office();
        validateAddView(view);
        setOrganizationToOffice(view, office);
        setResponseToModel(view, office);
        log.debug("office {} ", office.toString());
        dao.save(office);
    }

    /**
     * Проверить view фильтра организации на заполнение
     * обязательных полей
     * в случае ошибки вызвать собственное исключения
     *
     * @param officeFilterView
     */
    private void validateFilterView(OfficeFilterView officeFilterView) {
        if (Strings.isNullOrEmpty(officeFilterView.getOrgId())) {
            throw new CustomException("Не заполнено обязательное поле OrgId*");
        }
        if (!officeFilterView.getOrgId().matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID организации %s", officeFilterView.getOrgId()));
        }
    }

    /**
     * Проверить идентификатор
     * на числовой тип данных
     * в случае ошибки вызвать собственное исключения
     *
     * @param id
     */
    private void validateViewId(String id) {
        if (id == null) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID офиса %s", id));
        }
    }

    /**
     * Проверить view обновления офиса на заполнение
     * обязательных полей
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     */
    private void validateUpdateView(OfficeView view) {
        if (Strings.isNullOrEmpty(view.getId()) || Strings.isNullOrEmpty(view.getName())
                || Strings.isNullOrEmpty(view.getAddress()) || view.getActive() == null
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }
    }

    /**
     * Проверить view добавления офиса на заполнение
     * обязательных полей
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     */
    private void validateAddView(OfficeView view) {
        if (Strings.isNullOrEmpty(view.getOrgId())) {
            throw new CustomException("Не заполнено обязательное поле orgId* Oрганизации ");
        }
        if (!view.getOrgId().matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID организации %s", Integer.valueOf(view.getOrgId())));
        }
    }

    /**
     * Проверить office на null
     * в случае успеха записать в модель
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     * @param office
     */
    private void setResponseToModel(OfficeView view, Office office) {
        office.setName(view.getName());
        office.setPhone(view.getPhone());
        office.setAddress(view.getAddress());
        if (view.getActive() != null) {
            office.setActive(view.getActive());
        }
        log.debug("office {} ", office.toString());
    }

    /**
     * Проверить view на null
     * в случае успеха записать в view
     * в случае ошибки вызвать собственное исключения
     *
     * @param office
     * @return OfficeView
     */
    private OfficeView setResponseToView(Office office) {
        if (office == null) {
            return new OfficeView();
        }
        OfficeView officeView = new OfficeView();
        officeView.setId(Integer.toString(office.getId()));
        officeView.setName(office.getName());
        officeView.setPhone(office.getPhone());
        officeView.setAddress(office.getAddress());
        officeView.setActive(office.getActive());
        log.debug("office {} ", officeView);
        return officeView;
    }

    /**
     * Проверить организацию на null
     * в случае успеха установить организацию у офиса
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     * @param office
     */
    private void setOrganizationToOffice(OfficeView view, Office office) {
        Organization organization = dao.loadOrgById(Integer.valueOf(view.getOrgId()));
        if (organization == null) {
            throw new CustomException(String.format("Неверное ID организации %s", Integer.valueOf(view.getOrgId())));
        }
        office.setOrganization(organization);
    }
}