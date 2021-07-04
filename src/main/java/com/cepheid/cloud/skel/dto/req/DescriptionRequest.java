package com.cepheid.cloud.skel.dto.req;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel
public class DescriptionRequest implements IRequestValidatable, Serializable {

    private String descriptionComment;
    private ItemRequest itemRequest;

    public DescriptionRequest(String descriptionComment) {
        this.descriptionComment = descriptionComment;
        this.itemRequest = null;
    }

    public DescriptionRequest() {
    }

    public DescriptionRequest(String descriptionComment, ItemRequest itemRequest) {
        this.descriptionComment = descriptionComment;
        this.itemRequest = itemRequest;
    }


    public String getDescriptionComment() {
        return descriptionComment;
    }

    public ItemRequest getItemRequest() {
        return itemRequest;
    }

    public void setDescriptionComment(String descriptionComment) {
        this.descriptionComment = descriptionComment;
    }

    public void setItemRequest(ItemRequest itemRequest) {
        this.itemRequest = itemRequest;
    }

    @Override
    public void validate() {
        if (descriptionComment == null)
            throw new IllegalArgumentException("Description comment can not be null");
    }
}
