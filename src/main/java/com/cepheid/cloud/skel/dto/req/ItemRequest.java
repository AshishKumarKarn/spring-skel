package com.cepheid.cloud.skel.dto.req;

import com.cepheid.cloud.skel.model.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

@ApiModel
public class ItemRequest implements IRequestValidatable {

    private String name;
    private Item.State state;
    private List<DescriptionRequest> descriptions;

    public ItemRequest(String name, Item.State state, List<DescriptionRequest> descriptions) {
        this.name = name;
        this.state = state;
        this.descriptions = descriptions;
    }
    public ItemRequest(String name, Item.State state) {
        this.name = name;
        this.state = state;
        this.descriptions = new ArrayList<>();
    }

    public ItemRequest() {
    }

    public String getName() {
        return name;
    }

    public Item.State getState() {
        return state;
    }

    public List<DescriptionRequest> getDescriptions() {
        return descriptions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(Item.State state) {
        this.state = state;
    }

    public void setDescriptions(List<DescriptionRequest> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void validate() {
        if(this.name ==null)
            throw new IllegalArgumentException("name can not be null");
        if(this.state == null)
            throw new IllegalArgumentException("state can not be null");

        if(this.descriptions == null)
            throw new IllegalArgumentException("state can not be null");
    }
}
