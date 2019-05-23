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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao dao;
    private static final Pattern PATTERN = Pattern.compile("^\\d*$");
    private static final String EMPTY_DOCTYPECODE_OR_CITIZENSHIPCODE = "";

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
        validateId("officeId*", userFilterView.getOfficeId());
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
        validateId("Id*", id);
        User user = dao.loadUserById(Integer.valueOf(id));
        return setResponseToView(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUser(UserView view) {
        validateId("Id*", view.getId());
        User user = dao.loadUserById(Integer.valueOf(view.getId()));
        validateToNull(view.getId(), "сотрудник", user);
        setOfficeToUser(view, user);
        saveUser(view, user);
    }

    private void setOfficeToUser(UserView view, User user) {
        if (!(Strings.isNullOrEmpty(view.getOfficeId()))) {
            validateId("officeId*", view.getOfficeId());
            Office office = dao.loadOfficeById(view.getOfficeId());
            log.debug("office {} ", office);
            validateToNull(view.getOfficeId(), "офис", office);
            user.setOffice(office);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addUser(UserView view) {
        validateId("officeId*", view.getOfficeId());
        Office office = dao.loadOfficeById(view.getOfficeId());
        validateToNull(view.getOfficeId(), "офис", office);
        log.debug("office {} ", office);
        User user = new User();
        user.setOffice(office);
        saveUser(view, user);
    }

    /**
     * Сохранить сотрудника в user
     * по значениям переданным в view
     *
     * @param user, userView
     */
    private void saveUser(UserView view, User user) {
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
    private DocType getDocType(UserView view) {
        DocType docType;
        if (!(Strings.isNullOrEmpty(view.getDocCode()) && Strings.isNullOrEmpty(view.getDocName()))) {
            docType = dao.loadDocTypeByCodeAndName(view.getDocCode(), view.getDocName());
            if (docType == null) {
                throw new CustomException(String.format("Не найден тип документа с кодом %s с названием %s", view.getDocCode(), view.getDocName()));
            }
        } else {
            docType = dao.loadDocTypeByCodeAndName(EMPTY_DOCTYPECODE_OR_CITIZENSHIPCODE, EMPTY_DOCTYPECODE_OR_CITIZENSHIPCODE);
        }
        return docType;
    }

    /**
     * Найти по коду и наименованию документа сущность Country
     * в случае не соответстия вызвать собственное исключения
     * в случае если значения не заданы EMPTY_DOCTYPECODE_OR_CITIZENSHIPCODE вернуть сущность без кода и названия
     *
     * @param view
     * @return DocType
     */
    private Country getCitizenship(UserView view) {
        Country citizenship;
        if (!(Strings.isNullOrEmpty(view.getCitizenshipCode()) && Strings.isNullOrEmpty(view.getCitizenshipName()))) {
            citizenship = dao.loadCitizenshipByCodeAndName(view.getCitizenshipCode(), view.getCitizenshipName());
            if (citizenship == null) {
                throw new CustomException(String.format("Не найдена страна с кодом %s и с наименованием %s", view.getCitizenshipCode(), view.getCitizenshipName()));
            }
        } else {
            citizenship = dao.loadCitizenshipByCodeAndName(EMPTY_DOCTYPECODE_OR_CITIZENSHIPCODE, EMPTY_DOCTYPECODE_OR_CITIZENSHIPCODE);
        }
        return citizenship;
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
     * Проверить Object на null
     * в случае ошибки вызвать собственное исключения
     * viewId  и msg передаются в тексте этого искючения
     *
     * @param viewId
     * @param msg
     * @param object
     */
    private void validateToNull(String viewId, String msg, Object object) {
        if (object == null) {
            throw new CustomException(String.format("Не найден %s с Id = %s", msg, viewId));
        }
    }

    /**
     * Проверить id  на пустоту и null
     * в случае ошибки вызвать собственное исключения
     * Id  и msg передаются в тексте этого искючения
     *
     * @param id
     */
    private void validateId(String msg, String id) {
        log.debug("Id {}", id);
        if (Strings.isNullOrEmpty(id)) {
            throw new CustomException(String.format("Не заполнено обязательное поле %s сотрудника", msg));
        }
        if (!PATTERN.matcher(id).matches()) {
            throw new CustomException(String.format("Неверное у сотрудника %s = %s", msg, id));
        }
    }
}
