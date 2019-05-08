package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.controller.OfficeController;
import ru.bellintegrator.practice.service.OfficeService;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * {@inheritDoc}
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class OfficeControllerImpl implements OfficeController {

    /**
     * Логировние
     */
    private final Logger log = LoggerFactory.getLogger(OfficeControllerImpl.class);

    private final OfficeService officeService;

    @Autowired
    public OfficeControllerImpl(OfficeService officeService) {
        this.officeService = officeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/office/list", method = {POST})
    public List<OfficeFilterView> filterOffices(@RequestBody OfficeFilterView office) {
        log.info("office {} " + office.toString());
        List<OfficeFilterView> officeFilterViewList = officeService.filterOfficeList(office);
        log.info("list {}" + officeFilterViewList.toString());
        return officeFilterViewList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = {"/office", "/office/{id}"}, method = {GET})
    public OfficeView getOfficeById(@PathVariable(value = "id", required = false) String id) {
        log.info("office id{}" + id);
        OfficeView officeView = officeService.getOfficeById(id);
        log.info("office {}" + officeView.toString());
        return officeView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/office/update", method = {POST})
    public void updateOffice(@RequestBody OfficeView office) {
        log.info("Office {} " + office.toString());
        officeService.updateOffice(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/office/save", method = {POST})
    public void addOffice(@RequestBody OfficeView office) {
        log.info("office {} " + office.toString());
        officeService.addOffice(office);
    }

}

