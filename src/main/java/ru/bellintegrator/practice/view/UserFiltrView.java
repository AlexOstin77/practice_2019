package ru.bellintegrator.practice.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;


/**
 * JSON value
 * для отфильтрованного списка сотрудников
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class UserFiltrView {

    /**
     * Id сотрудника
     */
    private String id;

    /**
     * Id офиса
     */
    private String officeId;

    /**
     * Имя сотрудника
     */
    private String firstName;

    /**
     * Фамилия сотрудника
     */
    private String secondName;

    /**
     * Отчество сотрудника
     */
    private String middleName;

    /**
     * Должность сотрудника
     */
    private String possition;

    /**
     * Код документа
     */
    private String docCode;

    /**
     * Id страны
     */
    private String citizenShipCode;

    public UserFiltrView() {
    }

    public UserFiltrView(String id, String firstName, String secondName, String middleName, String possition) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.possition = possition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPossition() {
        return possition;
    }

    public void setPossition(String possition) {
        this.possition = possition;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getCitizenShipCode() {
        return citizenShipCode;
    }

    public void setCitizenShipCode(String citizenShipCode) {
        this.citizenShipCode = citizenShipCode;
    }

    @Override
    public String toString() {
        return "UserFiltrView{" +
                "id='" + id + '\'' +
                ", officeId='" + officeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", possition='" + possition + '\'' +
                ", docCode='" + docCode + '\'' +
                ", citizenShipCode='" + citizenShipCode + '\'' +
                '}';
    }
}