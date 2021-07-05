package com.cepheid.cloud.skel.dto.resp;

import io.swagger.annotations.ApiModel;

@ApiModel
public abstract class CephiedResponse {

    private final Boolean inError;
    private final String errorMsg;
    private final Long id;

    protected CephiedResponse(Boolean inError, String errorMsg, Long id) {
        this.inError = inError;
        this.errorMsg = errorMsg;
        this.id = id;
    }

    public Boolean isInError() {
        return inError;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Long getId() {
        return id;
    }
}
