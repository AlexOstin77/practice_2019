package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.controller.OrganizationController;
import ru.bellintegrator.practice.service.OrganizationService;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;
import java.util.List;
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

    private final OrganizationService organizationService;


    @Autowired
    public OrganizationControllerImpl(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/organization/list", method = {POST})
    public List<OrganizationFilterView> filterOrganizations(@RequestBody OrganizationFilterView organization) {
        List<OrganizationFilterView> organizationFilterView = organizationService.filterOrganizationList(organization);
        log.info("filtr {}" + organization);
        log.info("list {}" + organizationFilterView);
        return organizationFilterView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = {"/organization", "/organization/{id}"}, method = {GET})
    public OrganizationView getOrganizationById(@PathVariable(value = "id", required = false) String id) {
        OrganizationView organizationView = organizationService.getOrganizationById(id);
        log.info("organization {} " + organizationView);
        return organizationView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/organization/update", method = {POST})
    public void updateOrganizaton(@RequestBody OrganizationView organization) {
        log.info("update {} " + organization.toString());
        organizationService.updateOrganization(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/organiazation/save", method = {POST})
    public  void addOrganization(@RequestBody OrganizationView organization) {
        log.info("save {} " + organization.toString());
        organizationService.add(organization);
    }
}
