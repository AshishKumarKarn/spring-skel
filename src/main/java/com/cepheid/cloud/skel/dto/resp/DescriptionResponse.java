package com.cepheid.cloud.skel.dto.resp;


import io.swagger.annotations.ApiModel;

@ApiModel
public class DescriptionResponse extends CephiedResponse {

    private final String descriptionComment;


    public DescriptionResponse(Boolean inError,String errorMsg, String descriptionComment) {
        super(inError, null);
        this.descriptionComment = descriptionComment;
    }

    public DescriptionResponse(Boolean inError, String errorMsg) {
        super(inError, errorMsg);
        this.descriptionComment = null;
    }

    public String getDescriptionComment() {
        return descriptionComment;
    }

}
