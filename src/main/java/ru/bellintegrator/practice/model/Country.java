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

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        getUsers().add(user);
        user.setCountry(this);
    }
    public void removeUser(User user) {
        getUsers().remove(user);
        user.setCountry(null);
    }

    @Override
    public String toString() {
        return "Country{" +
                "version=" + version +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}