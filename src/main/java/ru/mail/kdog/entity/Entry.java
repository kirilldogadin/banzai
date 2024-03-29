package ru.mail.kdog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@XmlRootElement(name = "Entry")
@Entity
public class Entry extends BaseEntity{
    @Id
    @GeneratedValue
    @XmlTransient
    private Long id;

    private String creationDate;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationDate ()
    {
        return creationDate;
    }

    public void setCreationDate (String creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [creationDate = "+creationDate+", content = "+content+"]";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return id.equals(entry.id) &&
                creationDate.equals(entry.creationDate) &&
                Objects.equals(content, entry.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, content);
    }
}