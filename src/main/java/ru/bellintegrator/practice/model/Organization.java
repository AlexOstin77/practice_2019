package ru.bellintegrator.practice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "name", length = 50)
    @NotNull
    private String name;
    @Column(name = "full_name", length = 250)
    @NotNull
    private String fullName;
    @Column(name = "inn", length = 10)
    @NotNull
    private String inn;
    @Column(name = "kpp", length = 9)
    @NotNull
    private String kpp;
    @Column(name = "address", length = 250)
    @NotNull
    private String address;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Office> offices;

    public Organization() {
    }

    public Organization(String name, String fullName, String inn, String kpp, String address, String phone, Boolean isActive) {
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
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

    public void setOffice(List<Office> offices) {
        this.offices = offices;
    }

    public void addOffice(Office office) {
        offices.add(office);
        office.setOrganization(this);
    }

    public void removeOffice(Office office) {
        office.setOrganization(null);
    }


    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", name='" + name + '\'' + ", fullName='" + fullName + '\'' + ", inn=" + inn + ", kpp=" + kpp + ", address='" + address + '\'' + ", phone=" + phone + ", isActive=" + isActive + '}';
    }
}