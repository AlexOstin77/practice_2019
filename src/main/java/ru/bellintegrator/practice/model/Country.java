package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "code", length = 20)
    private String code;
    @Column(name = "name", length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name="country_doc",
            joinColumns=@JoinColumn (name="country_id"),
            inverseJoinColumns=@JoinColumn(name="doc_id"))
    private Set<DocType> docTypes;

    public Country() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(Set<DocType> docTypes) {
        this.docTypes = docTypes;
    }


    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", code=" + code + ", name='" + name + '\'' + '}';
    }
}