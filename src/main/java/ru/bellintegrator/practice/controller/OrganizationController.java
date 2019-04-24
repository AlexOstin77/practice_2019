package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

public interface OrganizationController {

    Response filterOrganizations(@RequestBody OrganizationFilterView organization);

    Response getOrganizationById(@PathVariable("id") String id) ;

    Response updateOrganizaton(@RequestBody OrganizationView organization);

    Response addOrganization(@RequestBody OrganizationView organization);

}
