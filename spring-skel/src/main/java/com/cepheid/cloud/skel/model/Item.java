package com.cepheid.cloud.skel.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@Entity
@Table( name = "item",
        indexes = {
                @Index(name = "I_NAME", columnList="name", unique = true),
                @Index(name = "I_STATE", columnList="state")})
public class Item extends AbstractEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Description> descriptions;

    public Item(){

    }

    public Item(String name, State state) {
        this.name = name;
        this.state = state;
        this.descriptions = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }

}
