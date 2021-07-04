package com.cepheid.cloud.skel.dto.resp;

import io.swagger.annotations.ApiModel;

@ApiModel
public abstract class CephiedResponse {

    private final Boolean inError;
    private final String errorMsg;

    protected CephiedResponse(Boolean inError, String errorMsg) {
        this.inError = inError;
        this.errorMsg = errorMsg;
    }

    public boolean isInError() {
        return inError;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
