package com.cepheid.cloud.skel.dto.resp;


import io.swagger.annotations.ApiModel;

@ApiModel
public class DescriptionResponse extends CephiedResponse {

    private final String descriptionComment;


    public DescriptionResponse(Boolean inError, String errorMsg, String descriptionComment, Long id) {
        super(inError, null, id);
        this.descriptionComment = descriptionComment;
    }

    public DescriptionResponse(Boolean inError, String errorMsg) {
        super(inError, errorMsg, null);
        this.descriptionComment = null;
    }

    public String getDescriptionComment() {
        return descriptionComment;
    }

}
