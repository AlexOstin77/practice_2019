package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.controller.OrganizationController;
import ru.bellintegrator.practice.service.OrganizationService;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
        log.debug("filter {} ", organization);
        log.debug("list {} ", organizationFilterView);
        return organizationFilterView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = {"/organization", "/organization/{id}"}, method = {GET})
    public OrganizationView getOrganizationById(@PathVariable(value = "id", required = false) String id) {
        OrganizationView organizationView = organizationService.getOrganizationById(id);
        log.debug("organization {} ", organizationView);
        return organizationView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/organization/update", method = {POST})
    public void updateOrganizaton(@RequestBody OrganizationView organization) {
        log.debug("update {} ", organization.toString());
        organizationService.updateOrganization(organization);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/organization/save", method = {POST})
    public void addOrganization(@RequestBody OrganizationView organization) {
        log.debug("save {} ", organization.toString());
        organizationService.add(organization);
    }
}
