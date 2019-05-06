package ru.bellintegrator.practice.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.UserDAO;
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.model.*;
import ru.bellintegrator.practice.service.UserService;
import ru.bellintegrator.practice.view.UserFiltrView;
import ru.bellintegrator.practice.view.UserView;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO dao;

    @Autowired
    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserFiltrView> filterUserList(UserFiltrView userFiltrView) {
        log.info("userFiltrView {} " + userFiltrView.toString());
        if (Strings.isNullOrEmpty(userFiltrView.getOfficeId())) {
            throw new CustomException("Не заполнено обязательное поле officeId*");
        }
        List<User> all = dao.filterUserList(userFiltrView);
        log.info("all {} " + all);
        Function<User, UserFiltrView> mapUser = p -> {
            UserFiltrView view = new UserFiltrView();
            view.setId(String.valueOf(p.getId()));
            view.setFirstName(p.getFirstName());
            view.setSecondName(p.getSecondName());
            view.setMiddleName(p.getMiddleName());
            view.setPossition(p.getPossition());
            log.info("list {} " + view);
            return view;
        };
        return all.stream().map(mapUser).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserView getUserById(String id) {
        log.info("Id  " + id);
        if (Strings.isNullOrEmpty(id)) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID сотрудника %s", id));
        }
        User user = dao.loadUserById(Integer.valueOf(id));
        UserView userView = new UserView();
        if (user == null) {
            return userView;
        }
        userView.setId(String.valueOf(user.getId()));
        userView.setFirstName(user.getFirstName());
        userView.setSecondName(user.getSecondName());
        userView.setMiddleName(user.getMiddleName());
        userView.setPossition(user.getPossition());
        userView.setDocCode(user.getDocument().getDocType().getCode());
        userView.setDocName(user.getDocument().getDocType().getName());
        userView.setDocNumber(user.getDocument().getNumber());
        userView.setDocDate(user.getDocument().getDate());
        userView.setCitizenshipCode(user.getCitizenship().getCode());
        userView.setCitizenshipName(user.getCitizenship().getName());
        userView.setPhone(user.getPhone());
        userView.setIdentified(user.getIdentified());
        log.info("userView {} " + userView);
        return userView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUser(UserView view) {
        if (Strings.isNullOrEmpty(view.getId())) {
            throw new CustomException("Не заполнено обязательное поле Id сотрудника ");
        }

        User user = dao.loadUserById(Integer.valueOf(view.getId()));
        if (user == null) {
            throw new CustomException(String.format("Не найден сотрудник с ID %s", view.getId()));

        }
        Office office = new Office();
        if (!(Strings.isNullOrEmpty(view.getOfficeId()))) {
            office = dao.loadOfficeById(view.getOfficeId());
            if (office == null) {
                throw new CustomException(String.format("Не найден офис с ID %s", view.getOfficeId()));
            }
        } else {
            office = dao.loadOfficeById("0");
        }
        user.setOffice(office);
        log.info("office {} " + office);
        saveUser(user, view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addUser(UserView view) {
        if (Strings.isNullOrEmpty(view.getOfficeId())
        ) {
            throw new CustomException("Не заполнено обязательное поле* ID офиса ");
        }
        Office office = dao.loadOfficeById(view.getOfficeId());
        if (office == null) {
            throw new CustomException(String.format("Не найден офис с ID %s", view.getOfficeId()));
        }
        log.info("office {} " + office);
        User user = new User();
        user.setOffice(office);
        saveUser(user, view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveUser(User user, UserView view) {
        if (Strings.isNullOrEmpty(view.getFirstName()) || Strings.isNullOrEmpty(view.getPossition())
        ) {
            throw new CustomException("Не заполнены обязательные поля* ");
        }
        Country citizenship;
        DocType docType;
        Document document = user.getDocument();
        if (document == null) {
            document = new Document();
        }
        if (!(Strings.isNullOrEmpty(view.getDocCode()) && Strings.isNullOrEmpty(view.getDocName()))) {
            docType = dao.loadDocTypeByCodeAndName(view.getDocCode(), view.getDocName());
            if (docType == null) {
                throw new CustomException(String.format("Не найден тип документа с кодом %s с названием %s", view.getDocCode(), view.getDocName()));
            }
        } else {
            docType = dao.loadDocTypeByCodeAndName("", "");
        }

        if (!(Strings.isNullOrEmpty(view.getCitizenshipCode()) && Strings.isNullOrEmpty(view.getCitizenshipName()))) {
            citizenship = dao.loadCitizenshipByCodeAndName(view.getCitizenshipCode(), view.getCitizenshipName());
            if (citizenship == null) {
                throw new CustomException(String.format("Не найдена страна с кодом %s и с наименованием %s", view.getCitizenshipCode(), view.getCitizenshipName()));
            }
        } else {
            citizenship = dao.loadCitizenshipByCodeAndName("", "");
        }
        log.info("view {} " + view);
        log.info("docType {} " + docType);
        log.info("citizen {} " + citizenship);
        log.info("document {} " + document);
        document.setDocType(docType);
        user.setDocument(document);
        user.setCitizenship(citizenship);
        user.setFirstName(view.getFirstName());
        user.setPossition(view.getPossition());
        user.setSecondName(view.getSecondName());
        user.setMiddleName(view.getMiddleName());
        user.setPhone(view.getPhone());
        user.getDocument().setDate(view.getDocDate());
        user.getDocument().setNumber(view.getDocNumber());
        if (view.getIdentified() != null) {
            user.setIdentified(view.getIdentified());
        }
        log.info("user {} " + user);
        dao.save(user);
    }
}
