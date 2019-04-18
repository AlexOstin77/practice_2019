package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "doc_detail")
public class DocType {
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public DocType() {
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

    @Override
    public String toString() {
        return "DocType{" +
                "docNumber='" + docNumber + '\'' +
                ", docDate=" + docDate +
                '}';
    }
}
