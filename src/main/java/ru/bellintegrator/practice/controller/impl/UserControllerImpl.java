package ru.bellintegrator.practice.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.practice.controller.UserController;
import ru.bellintegrator.practice.service.UserService;
import ru.bellintegrator.practice.view.UserFilterView;
import ru.bellintegrator.practice.view.UserView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * {@inheritDoc}
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {

    /**
     * Логировние
     */
    private final Logger log = LoggerFactory.getLogger(OfficeControllerImpl.class);

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/user/list", method = {POST})
    public List<UserFilterView> filterUsers(@RequestBody UserFilterView userFilterView) {
        List<UserFilterView> userFilterViewList = userService.filterUserList(userFilterView);
        log.debug("userFilterView  {} ", userFilterView);
        log.debug("userFilterViewList {} ", userFilterViewList);
        return userFilterViewList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = {"/user/", "/user/{id}"}, method = {GET})
    public UserView getUserById(@PathVariable(value = "id", required = false) String id) {
        UserView userView = userService.getUserById(id);
        log.debug("id " + id + " userView {} ", userView.toString());
        return userView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/user/update", method = {POST})
    public void updateUser(@RequestBody UserView user) {
        log.debug("user {} ", user);
        userService.updateUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "/user/save", method = {POST})
    public void addUser(@RequestBody UserView user) {
        log.debug("user {} ", user);
        userService.addUser(user);
    }
}

