package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "document")
public class Document {
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

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="country_doc",
            joinColumns=@JoinColumn(name="document_id"),
            inverseJoinColumns=@JoinColumn(name="country_id"))
    private Set<Country> countries;

    public Document() {
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

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public void addUser(User user) {
        getUsers().add(user);
        user.setDocument(this);
    }
    public void removeUser(User user) {
        getUsers().remove(user);
        user.setDocument(null);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Document{" + "id=" + id + ", code=" + code + ", name='" + name + '\'' + ", countries=" + countries + '}';
    }
}
