package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "doc_detail")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "number", length = 20)
    private String number;
    @Column(name = "date")
    private Date date;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docType_id")
    private DocType docType;

    public Document() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    @Override
    public String toString() {
        return "Document{" +
                "version=" + version +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", docType=" + docType +
                '}';
    }
}
