package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.controller.OfficeController;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.message.ResponseSuccess;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
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

    @Override
    @RequestMapping(value = "/office/list", method = {POST})
    public Response filterOffices(@RequestBody OfficeFilterView office) {
        log.info("office {} " + office.toString());
        List<OfficeFilterView> officeFilterViewList = new ArrayList<>();
        officeFilterViewList.add(new OfficeFilterView("1", "АДМИНИСТРАЦИЯ", TRUE));
        officeFilterViewList.add(new OfficeFilterView("2", "БУХГАЛТЕРИЯ", FALSE));
        log.info("list {}" + officeFilterViewList.toString());
        return new ResponseSuccess("success", officeFilterViewList);
    }

    @Override
    @RequestMapping(value = "/office/{id}", method = {GET})
    public Response getOfficeById(@PathVariable(value = "id") String id) {
        OfficeView officeView = new OfficeView("1", "АДМИНИСТРАЦИЯ", "Г.ПЕНЗА УЛ. ЛЕНИНА Д.777", "84125565", TRUE);
        log.info("office {}" + officeView.toString());
        return new ResponseSuccess("success", officeView);
    }

    @Override
    @RequestMapping(value = "/office/update", method = {POST})
    public Response updateOffice(@RequestBody OfficeView office) {
        log.info("Office {} " + office.toString());
        return new ResponseSuccess("success");
    }

    @Override
    @RequestMapping(value = "/office/save", method = {POST})
    public Response addOffice(@RequestBody OfficeView office) {
        log.info("office {} " + office.toString());
        return new ResponseSuccess("success");
    }

}

