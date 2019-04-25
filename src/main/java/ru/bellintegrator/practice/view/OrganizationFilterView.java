package ru.bellintegrator.practice.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)

/**
 * JSON value
 * для отфильтрованного списка организации
 *
 */
public class OrganizationFilterView {

    private String id;

    private String name;

    private String inn;

    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public OrganizationFilterView() {

    }

    public OrganizationFilterView(String id, String name, String inn, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.isActive = isActive;
    }

    public OrganizationFilterView(String id, String name, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "OrganizationFilterView{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}