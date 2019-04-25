package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.controller.UserController;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.message.ResponseSuccess;
import ru.bellintegrator.practice.view.UserFiltrView;
import ru.bellintegrator.practice.view.UserView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * {@inheritDoc}
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {
    private final Logger log = LoggerFactory.getLogger(OfficeControllerImpl.class);

    @Override
    @RequestMapping(value = "/user/list", method = {POST})
    public Response filterUsers(@RequestBody UserFiltrView user) {
        log.info("user {}" + user.toString());
        List<UserFiltrView> userFiltrViewList = new ArrayList<>();
        userFiltrViewList.add(new UserFiltrView("1", "ИВАН", "ИВАНОВИЧ", "ИВАНОВ", "ГЕНЕРАЛЬНЫЙ ДИРЕКТОР"));
        userFiltrViewList.add(new UserFiltrView("2", "ПЁТР", "ПЕТРОВИЧ", "ПЕТРОВ", "ГЛАВНЫЙ БУХГАЛТЕР"));
        return new ResponseSuccess("success", userFiltrViewList);
    }

    @Override
    @RequestMapping(value = "/user/{id}", method = {GET})
    public Response getUserById(@PathVariable(value = "id") Integer id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date date = new Date(0);
        try {
            date = format.parse("2009-12-31");
        } catch (ParseException e) {
            log.info("Нераспаршена date " + e);
        }
        UserView user = new UserView("1", "ИВАН", "ИВАНОВИЧ", "ИВАНОВ", "ГЕНЕРАЛЬНЫЙ ДИРЕКТОР", "94512345", "Паспорт гражданина РФ", "643", "123321", date, "Граждаство Уругвая", "500", TRUE);
        log.info("id " + id.toString() + " user {} " + user.toString());
        return new ResponseSuccess("success", user);
    }

    @Override
    @RequestMapping(value = "/user/update", method = {POST})
    public Response updateUser(@RequestBody UserView user) {
        log.info("user {} " + user.toString());
        return new ResponseSuccess("success");
    }

    @Override
    @RequestMapping(value = "/user/save", method = {POST})
    public Response saveUser(@RequestBody UserView user) {
        log.info("user {} " + user.toString());
        return new ResponseSuccess("success");
    }
}

