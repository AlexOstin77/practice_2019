package ru.bellintegrator.practice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "office")

public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "name", length = 50)
    @NotNull
    private String name;
    @Column(name = "address", length = 250)
    @NotNull
    private String address;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id")
    private Organization organization;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    public Office() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        getUsers().add(user);
        user.setOffice(this);
    }
    public void removeUser(User user) {
        getUsers().remove(user);
        user.setOffice(null);
    }

    @Override
    public String toString() {
        return "Office{" + "id=" + id + ", name='" + name + '\'' + ", address='" + address + '\'' + ", phone=" + phone + ", isActive=" + isActive + ", organization=" + organization + ", users=" + users + '}';
    }
}