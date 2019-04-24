package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;

public interface OfficeController {

    Response filterOffices(@RequestBody OfficeFilterView office);

    Response getOfficeById(@PathVariable("id") String id) ;

    Response updateOffice(@RequestBody OfficeView office);

    Response addOffice(@RequestBody OfficeView office);
}
