package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "doc_detail")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "doc_number", length = 20)
    private String docNumber;
    @Column(name = "doc_date")
    private Date docDate;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocType> docTypes;

    public Document() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DocType> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(List<DocType> docTypes) {
        this.docTypes = docTypes;
    }

    public void addDocType(DocType docType) {
        getDocTypes().add(docType);
        docType.setDocument(this);
    }
    public void removeDocType(DocType docType) {
        getDocTypes().remove(docType);
        docType.setDocument(null);
    }

    @Override
    public String toString() {
        return "Document{" +
                "version=" + version +
                ", docNumber='" + docNumber + '\'' +
                ", docDate=" + docDate +
                ", user=" + user +
                ", docTypes=" + docTypes +
                '}';
    }
}
