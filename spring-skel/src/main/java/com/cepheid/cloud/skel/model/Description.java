package com.cepheid.cloud.skel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Description extends AbstractEntity {
    private String summary;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id")
    private Item item;

    private Type type;

    public Description(){

    }

    public Description(String summary, Type type) {
        this.summary = summary;
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Description otherDescription = (Description)obj;
        return this.summary.equals(otherDescription.getSummary()) &&
                this.item == otherDescription.getItem() &&
                this.type == otherDescription.getType();
    }
}
