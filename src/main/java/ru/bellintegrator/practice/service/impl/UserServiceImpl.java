package ru.bellintegrator.practice.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.dao.UserDao;
import ru.bellintegrator.practice.exception.CustomException;
import ru.bellintegrator.practice.model.Country;
import ru.bellintegrator.practice.model.DocType;
import ru.bellintegrator.practice.model.Document;
import ru.bellintegrator.practice.model.Office;
import ru.bellintegrator.practice.model.User;
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
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserFiltrView> filterUserList(UserFiltrView userFiltrView) {
        log.info("userFiltrView {} " + userFiltrView.toString());
        validateOfficeId(userFiltrView.getOfficeId());
        List<User> all = dao.filterUserList(userFiltrView.getOfficeId(), userFiltrView.getFirstName(), userFiltrView.getSecondName(), userFiltrView.getMiddleName(), userFiltrView.getPossition(), userFiltrView.getDocCode(), userFiltrView.getCitizenshipCode());
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
        validateId(id);
        User user = dao.loadUserById(Integer.valueOf(id));
        return setResponseToView(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUser(UserView view) {
        validateId(view.getId());
        User user = dao.loadUserById(Integer.valueOf(view.getId()));
        validateViewOfUpdate(view, user);
        Office office = new Office();
        if (!(Strings.isNullOrEmpty(view.getOfficeId()))) {
            office = dao.loadOfficeById(view.getOfficeId());
            validateOfficeToNull(view, office);
        } else {
            office = dao.loadOfficeById("0");
        }
        user.setOffice(office);
        log.info("office {} " + office);
        saveUser(view, user);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addUser(UserView view) {
        validateOfficeId(view.getOfficeId());
        Office office = dao.loadOfficeById(view.getOfficeId());
        validateOfficeToNull(view, office);
        log.info("office {} " + office);
        User user = new User();
        user.setOffice(office);
        saveUser(view, user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveUser(UserView view, User user) {
        if (Strings.isNullOrEmpty(view.getFirstName()) || Strings.isNullOrEmpty(view.getPossition())) {
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

    /**
     * Проверить идентификатор
     * на числовой тип данных
     * в случае ошибки вызвать собственное исключения
     *
     * @param id
     */
    private void validateId(String id) {
        log.info("Id  " + id);
        if (Strings.isNullOrEmpty(id)) {
            throw new CustomException("Не заполнено обязательное поле Id* ");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное ID сотрудника %s", id));
        }
    }

    /**
     * Проверить view на null
     * в случае успеха записать в модель
     * в случае ошибки вызвать собственное исключения
     *
     * @param user
     * @return UserView
     */
    private UserView setResponseToView(User user) {
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
     * Проверить view при обновления сотрудника на null
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     * @param user
     */
    private void validateViewOfUpdate(UserView view, User user) {
        if (user == null) {
            throw new CustomException(String.format("Не найден сотрудник с ID %s", view.getId()));
        }
    }

    /**
     * Проверить office на null
     * в случае ошибки вызвать собственное исключения
     *
     * @param view
     * @param office
     */
    private void validateOfficeToNull(UserView view, Office office) {
        if (office == null) {
            throw new CustomException(String.format("Не найден офис с ID %s", view.getOfficeId()));
        }
    }


    /**
     * Проверить id  office на заполнение
     * в случае ошибки вызвать собственное исключения
     *
     * @param id
     */
    private void validateOfficeId(String id) {
        if (Strings.isNullOrEmpty(id)) {
            throw new CustomException("Не заполнено обязательное поле* ID офиса ");
        }
    }
}
