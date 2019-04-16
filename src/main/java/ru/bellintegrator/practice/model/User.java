package ru.bellintegrator.practice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "first_name", length = 50)
    @NotNull
    private String firstName;
    @Column(name = "second_name", length = 50)
    private String secondName;
    @Column(name = "middle_name", length = 50)
    private String middleName;
    @Column(name = "possition", length = 50)
    @NotNull
    private String possition;
    @Column(name = "doc_number", length = 20)
    private String docNumber;
    @Column(name = "doc_date")
    private Date docDate;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "is_identified")
    private Boolean isIdentified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Doc doc;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPossition() {
        return possition;
    }

    public void setPossition(String possition) {
        this.possition = possition;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    @Override
    public String toString() {
        return "User{" + "firstName='" + firstName + '\'' + ", secondName='" + secondName + '\'' + ", middleName='" + middleName + '\'' + ", possition='" + possition + '\'' + ", docNumber=" + docNumber + ", docDate=" + docDate + ", phone=" + phone + ", isIdentified=" + isIdentified + '}';
    }
}
