package ru.bellintegrator.practice.model;

import javax.persistence.*;

/**
 * Страна
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
     * Код страны
     */
    @Column(name = "code", length = 20)
    private String code;

    /**
     * Наименование страны
     */
    @Column(name = "name", length = 50)
    private String name;

    public Country() {
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