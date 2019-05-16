package ru.bellintegrator.practice.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * JSON value
 * для отфильтрованного списка офисов
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class OfficeFilterView {

    /**
     * Id офиса
     */
    private String id;

    /**
     * Название офиса
     */
    private String name;

    /**
     * Телефон офиса
     */
    private String phone;

    /**
     * Признак верификации офиса
     */
    private Boolean isActive;

    /**
     * Id организации
     */
    private String orgId;

    public OfficeFilterView() {
    }

    public OfficeFilterView(String id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public OfficeFilterView(String name, Boolean isActive, String orgId) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "OfficeFilterView{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                ", orgId='" + orgId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeFilterView)) return false;
        OfficeFilterView that = (OfficeFilterView) o;
        return getOrgId().equals(that.getOrgId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrgId());
    }
}