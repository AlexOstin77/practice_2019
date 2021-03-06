package ru.bellintegrator.practice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Офис, место приклепления сотрудника,
 * подчиненная структура для организации.
 */
@Entity
@Table(name = "office")
public class Office {

    /**
     * Id офиса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Служебное поле hibernate
     */
    @Version
    @Column(name = "version")
    @NotNull
    private Integer version;

    /**
     * Название офиса
     */
    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    /**
     * Адрес офиса
     */
    @Column(name = "address", length = 250)
    @NotNull
    private String address;

    /**
     * Телефон офиса
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Признак прохождения верификации
     */
    @Column(name = "is_active")
    private boolean isActive;

    /**
     * Id организации
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "Office{" +
                "version=" + version +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                ", organization=" + organization +
                '}';
    }
}