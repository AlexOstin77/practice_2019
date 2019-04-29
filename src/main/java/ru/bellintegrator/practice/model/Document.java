package ru.bellintegrator.practice.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Документ
 */
@Entity
@Table(name = "doc_type")
public class Document {

    /**
     * Id документа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Служебное поле hibernate
     */
    @Version
    @Column(name = "version")
    @NotNull
    private Integer version;

    /**
     * Номер документа
     */
    @Column(name = "number", length = 20)
    private String number;

    /**
     * Дата документа
     */
    @Column(name = "date")
    private Date date;

    /**
     * Id типа документа
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docType_id")
    private DocType docType;

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
