package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.controller.OrganizationController;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.message.ResponseSuccess;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * {@inheritDoc}
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class OrganizationControllerImpl implements OrganizationController {

    /**
     * Логировние
     */
    private final Logger log = LoggerFactory.getLogger(OrganizationControllerImpl.class);

    @Override
    @RequestMapping(value = "/organization/list", method = {POST})
    public Response filterOrganizations(@RequestBody OrganizationFilterView organization) {
        List<OrganizationFilterView> viewList = new ArrayList<>();
        OrganizationFilterView organizationFilterView = new OrganizationFilterView("1", "ООО СТАРТ", TRUE);
        viewList.add(new OrganizationFilterView("1", "ООО СТАРТ", TRUE));
        viewList.add(new OrganizationFilterView("2", "ЗAО МИР", TRUE));
        log.info("list {}" + viewList);
        return new ResponseSuccess("success", viewList);
    }

    @Override
    @RequestMapping(value = {"/organization", "/organization/{id}"}, method = {GET})
    public Response getOrganizationById(@PathVariable(value = "id", required = false) String id) {
        OrganizationView organizationView = new OrganizationView("1", "ООО СТАРТ", "ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕСТВЕННОСТЬЮ СТАРТ", "5820001122", "582001001", "ПЕНЗА УЛ.КРАСНАЯ Д. 1", "541234", TRUE);
        log.info("organization {} " + organizationView);
        return new ResponseSuccess("success", organizationView);
    }

    @Override
    @RequestMapping(value = "/organization/update", method = {POST})
    public Response updateOrganizaton(@RequestBody OrganizationView organization) {
        log.info("update {} " + organization.toString());
        return new ResponseSuccess("success");
    }

    @Override
    @RequestMapping(value = "/organiazation/save", method = {POST})
    public Response addOrganization(@RequestBody OrganizationView organization) {
        log.info("save {} " + organization.toString());
        return new ResponseSuccess("success");
    }


}
