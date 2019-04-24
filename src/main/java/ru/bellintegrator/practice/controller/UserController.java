package ru.bellintegrator.practice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.message.Response;
import ru.bellintegrator.practice.view.UserFiltrView;
import ru.bellintegrator.practice.view.UserView;

public interface UserController {

    Response filterUsers(@RequestBody UserFiltrView user);

    Response getUserById(@PathVariable("id") Integer id);

    Response updateUser(@RequestBody UserView user);

    Response saveUser(@RequestBody UserView user);

}
