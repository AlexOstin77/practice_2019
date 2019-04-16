package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doc")
public class Doc {
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

    @OneToMany(mappedBy = "doc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="country_doc",
            joinColumns=@JoinColumn(name="doc_id"),
            inverseJoinColumns=@JoinColumn(name="country_id"))
    private List<Country> countries;

    public Doc() {
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

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void addUser(User user) {
        getUsers().add(user);
        user.setDoc(this);
    }
    public void removeUser(User user) {
        getUsers().remove(user);
        user.setDoc(null);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Doc{" + "id=" + id + ", code=" + code + ", name='" + name + '\'' + ", countries=" + countries + '}';
    }
}
