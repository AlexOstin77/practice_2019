package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable (name="country_doc",
            joinColumns=@JoinColumn (name="country_id"),
            inverseJoinColumns=@JoinColumn(name="doc_type_id"))
    private Set<DocType> docTypes;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(Set<DocType> docTypes) {
        this.docTypes = docTypes;
    }

    public void addDocType(DocType docType) {
        docTypes.add(docType);
        docType.getCountries().add(this);
    }
    public void removeDocType(DocType docType) {
        docTypes.remove(docType);
        docType.getCountries().remove(this);
    }

    @Override
    public String toString() {
        return "Country{" +
                "version=" + version +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}