package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Гражданство сотрудника
 */
@Entity
@Table(name = "country")
public class Country {
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
     * Код граждаства
     */
    @Column(name = "code", length = 20)
    private String code;

    /**
     * Наименование граждаства
     */
    @Column(name = "name", length = 50)
    private String name;

    public Country() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "version=" + version +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}