package ru.bellintegrator.practice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Страна
 */
@Entity
@Table(name = "country")
public class Country {

    /**
     * Id страны
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
     * Код страны
     */
    @Column(name = "code", length = 20)
    private String code;

    /**
     * Наименование страны
     */
    @Column(name = "name", length = 50)
    private String name;

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