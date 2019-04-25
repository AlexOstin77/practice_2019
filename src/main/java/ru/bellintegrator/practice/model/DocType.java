package ru.bellintegrator.practice.model;

import javax.persistence.*;

/**
 * Тип докумнта
 */
@Entity
@Table(name = "doc_type")
public class DocType {
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
     * Код типа документа
     */
    @Column(name = "code", length = 20)
    private String code;

    /**
     * Наименование типа документа
     */
    @Column(name = "name", length = 250)
    private String name;

    public DocType() {
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
        return "DocType{" +
                "version=" + version +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
