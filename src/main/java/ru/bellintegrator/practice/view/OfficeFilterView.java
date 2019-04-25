package ru.bellintegrator.practice.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)

/**
 * JSON value
 * для отфильтрованного списка офисов
 *
 */
public class OfficeFilterView {

    private String id;

    private String name;

    private String phone;

    private Boolean isActive;

    private String orgId;

    public OfficeFilterView() {
    }

    public OfficeFilterView(String id, String name, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
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
}