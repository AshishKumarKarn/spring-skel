package com.cepheid.cloud.skel.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
public class Item extends AbstractEntity {

    private String name;
    private State state;


    @JsonManagedReference
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Description> descriptions = new ArrayList<>();

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

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public Item addDescription(Description description) {
        descriptions.add(description);
        description.setItem(this);
        return this;
    }

    public Item removeDescription(Description description) {
        descriptions.remove(description);
        description.setItem(null);
        return this;
    }

    public enum State {
        UNDEFINED, VALID, INVALID
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("state=" + state)
                .add("descriptions=" + descriptions)
                .toString();
    }
}
