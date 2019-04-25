package ru.bellintegrator.practice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Организация сотрудника
 */
@Entity
@Table(name = "organization")
public class Organization {
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
     * Краткое наименование организации
     */
    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    /**
     * Полное наименование организации
     */
    @Column(name = "full_name", length = 250)
    @NotNull
    private String fullName;

    /**
     * ИНН организации
     */
    @Column(name = "inn", length = 10)
    @NotNull
    private String inn;

    /**
     * КПП организации
     */
    @Column(name = "kpp", length = 9)
    @NotNull
    private String kpp;

    /**
     * Адрес организации
     */
    @Column(name = "address", length = 250)
    @NotNull
    private String address;

    /**
     * Телефон организации
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Признак прохождения верификации организации
     */
    @Column(name = "is_active")
    private Boolean isActive;

    public Organization() {
    }

    public Organization(String name, String fullName, String inn, String kpp, String address, String phone, Boolean isActive) {
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "version=" + version +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}