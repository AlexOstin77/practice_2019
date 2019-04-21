package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doc_type")
public class DocType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "code", length = 20)
    private String code;
    @Column(name = "name", length = 250)
    private String name;

    @OneToMany(mappedBy = "docType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="country_doc",
            joinColumns=@JoinColumn(name="doc_type_id"),
            inverseJoinColumns=@JoinColumn(name="country_id"))
    private Set<Country> countries;

    public DocType() {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        getUsers().add(user);
        user.setDocType(this);
    }
    public void removeUser(User user) {
        getUsers().remove(user);
        user.setDocType(null);
    }


    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }


    public void addCountry(Country country) {
        countries.add(country);
        country.getDocTypes().add(this);
    }
    public void removeCountry(Country country) {
        countries.remove(country);
        country.getDocTypes().remove(this);
    }


    @Override
    public String toString() {
        return "DocType{" +
                "version=" + version +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", countries=" + countries +
                '}';
    }
}
