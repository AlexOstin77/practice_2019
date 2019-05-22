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
import ru.bellintegrator.practice.view.UserFilterView;
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
    public List<UserFilterView> filterUserList(UserFilterView userFilterView) {
        log.debug("userFilterView {} ", userFilterView.toString());
        validateOfficeId(userFilterView.getOfficeId());
        List<User> all = dao.filterUserList(userFilterView.getOfficeId(), userFilterView.getFirstName(), userFilterView.getSecondName(), userFilterView.getMiddleName(), userFilterView.getPossition(), userFilterView.getDocCode(), userFilterView.getCitizenshipCode());
        log.debug("all {} ", all);
        Function<User, UserFilterView> mapUser = p -> {
            UserFilterView view = new UserFilterView();
            view.setId(String.valueOf(p.getId()));
            view.setFirstName(p.getFirstName());
            view.setSecondName(p.getSecondName());
            view.setMiddleName(p.getMiddleName());
            view.setPossition(p.getPossition());
            log.debug("list {} ", view);
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
        validateUserId(id);
        User user = dao.loadUserById(Integer.valueOf(id));
        return setResponseToView(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUser(UserView view) {
        validateUserId(view.getId());
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
        log.debug("office {} ", office);
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
        log.debug("office {} ", office);
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
            throw new CustomException("Не заполнены все обязательные поля* сотрудника");
        }
        Country citizenship = getCitizenship(view);
        DocType docType = getDocType(view);
        Document document = user.getDocument();
        if (document == null) {
            document = new Document();
        }
        log.debug("view {}, docType {}, citizen {}, document {} ", view, docType, citizenship, document);
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
        log.debug("user {} ", user);
        dao.save(user);
    }

    /**
     * Найти по коду и наименованию документа сущность DocType
     * в случае не соответстия вызвать собственное исключения
     * в случае если значения не заданы вернуть сущность без кода и названия
     *
     * @param view
     * @return DocType
     */
    @Transactional
    private DocType getDocType(UserView view) {
        DocType docType;
        if (!(Strings.isNullOrEmpty(view.getDocCode()) && Strings.isNullOrEmpty(view.getDocName()))) {
            docType = dao.loadDocTypeByCodeAndName(view.getDocCode(), view.getDocName());
            if (docType == null) {
                throw new CustomException(String.format("Не найден тип документа с кодом %s с названием %s", view.getDocCode(), view.getDocName()));
            }
        } else {
            docType = dao.loadDocTypeByCodeAndName("", "");
        }
        return docType;
    }

    /**
     * Найти по коду и наименованию документа сущность Country
     * в случае не соответстия вызвать собственное исключения
     * в случае если значения не заданы вернуть сущность без кода и названия
     *
     * @param view
     * @return DocType
     */
    @Transactional
    private Country getCitizenship(UserView view) {
        Country citizenship;
        if (!(Strings.isNullOrEmpty(view.getCitizenshipCode()) && Strings.isNullOrEmpty(view.getCitizenshipName()))) {
            citizenship = dao.loadCitizenshipByCodeAndName(view.getCitizenshipCode(), view.getCitizenshipName());
            if (citizenship == null) {
                throw new CustomException(String.format("Не найдена страна с кодом %s и с наименованием %s", view.getCitizenshipCode(), view.getCitizenshipName()));
            }
        } else {
            citizenship = dao.loadCitizenshipByCodeAndName("", "");
        }
        return citizenship;
    }

    /**
     * Проверить идентификатор
     * на числовой тип данных
     * в случае ошибки вызвать собственное исключения
     *
     * @param id
     */
    private void validateUserId(String id) {
        log.debug("Id  " + id);
        if (Strings.isNullOrEmpty(id)) {
            throw new CustomException("Не заполнено обязательное поле Id* сотрудника");
        }
        if (!id.matches("^\\d*$")) {
            throw new CustomException(String.format("Неверное Id сотрудника %s", id));
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
        log.debug("userView {} ", userView);
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
            throw new CustomException(String.format("Не найден сотрудник с Id %s", view.getId()));
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
            throw new CustomException(String.format("Не найден офис с Id %s", view.getOfficeId()));
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
            throw new CustomException("Не заполнено обязательное поле officeId* сотрудника");
        }
    }
}
