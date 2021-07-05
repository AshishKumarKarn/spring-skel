package com.cepheid.cloud.skel.dto.resp;

import com.cepheid.cloud.skel.model.Item;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class ItemResponse extends CephiedResponse {

    private final String name;
    private final Item.State state;
    private final List<DescriptionResponse> descriptionResponses;

    public ItemResponse(Boolean inError, String name, Item.State state, List<DescriptionResponse> descriptionResponses, Long id) {
        super(inError, null, id);
        this.name = name;
        this.state = state;
        this.descriptionResponses = descriptionResponses;
    }

    public ItemResponse(Boolean inError, String errorMsg) {
        super(inError, errorMsg, null);
        this.name = null;
        this.state = null;
        this.descriptionResponses = null;
    }

    public String getName() {
        return name;
    }

    public Item.State getState() {
        return state;
    }

    public List<DescriptionResponse> getDescriptionResponses() {
        return descriptionResponses;
    }
}
