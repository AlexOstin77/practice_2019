package ru.bellintegrator.practice.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doc_type")
public class DocType {
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

    @OneToMany(mappedBy = "docType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> document;

    public DocType() {
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

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

    public void addDocument(Document document) {
        getDocument().add(document);
        document.setDocType(this);
    }
    public void removeDocument(Document document) {
        getDocument().remove(document);
        document.setDocType(null);
    }

    @Override
    public String toString() {
        return "DocType{" +
                "version=" + version +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", document=" + document +
                '}';
    }
}
