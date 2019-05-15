package ru.bellintegrator.practice.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;


/**
 * JSON value
 * для обновления, сохранения, поиска организации
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class OrganizationView {

    /**
     * Id организации
     */
    private String id;

    /**
     * Краткое наименование организации
     */
    private String name;

    /**
     * Полное наименование организации
     */
    private String fullName;

    /**
     * ИНН организации
     */
    private String inn;

    /**
     * КПП организации
     */
    private String kpp;

    /**
     * Адрес организации
     */
    private String address;

    /**
     * Телефон организации
     */
    private String phone;

    /**
     * Признак верификации организации
     */
    private Boolean isActive;

    public OrganizationView() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return this.isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "OrganizationView{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", fullName='" + fullName + '\'' + ", inn=" + inn + ", kpp=" + kpp + ", address='" + address + '\'' + ", phone=" + phone + ", isActive=" + isActive + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationView)) return false;
        OrganizationView that = (OrganizationView) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getFullName().equals(that.getFullName()) &&
                getInn().equals(that.getInn()) &&
                getKpp().equals(that.getKpp()) &&
                getAddress().equals(that.getAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getFullName(), getInn(), getKpp(), getAddress(), getPhone(), isActive);
    }
}