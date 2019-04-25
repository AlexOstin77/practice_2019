package ru.bellintegrator.practice.model;

import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Сотрудники  работающие в офисах организации
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * Id сотрудника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Служебное поле hibernate
     */
    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * Имя сотрудника
     */
    @Column(name = "first_name", length = 50)
    @NotNull
    private String firstName;

    /**
     * Фамилия сотрудника
     */
    @Column(name = "second_name", length = 50)
    private String secondName;

    /**
     * Отчество сотрудника
     */
    @Column(name = "middle_name", length = 50)
    private String middleName;

    /**
     * Должность сотрудника
     */
    @Column(name = "possition", length = 50)
    @NotNull
    private String possition;

    /**
     * Телефон сотрудника
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Признак прохохдения верификации сотрудника
     */
    @Column(name = "is_identified")
    private Boolean isIdentified;

    /**
     * Id страны
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country citizenshipCode;

    /**
     * Id документа
     */
    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="document_id", nullable = false)
    private Document document;

    /**
     * Id офиса
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Country getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(Country citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "User{" +
                "version=" + version +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", possition='" + possition + '\'' +
                ", phone='" + phone + '\'' +
                ", isIdentified=" + isIdentified +
                ", citizenshipCode=" + citizenshipCode +
                ", document=" + document +
                ", office=" + office +
                '}';
    }
}
