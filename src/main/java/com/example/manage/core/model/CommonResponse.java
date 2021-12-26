package com.example.manage.core.model;

import com.example.manage.core.ResultCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private int code = ResultCodes.OK.value();
    private boolean success = true;
    private String message;
    private T data;


    public void setSuccess(boolean success) {
        this.success = success;
        this.code = success ? ResultCodes.OK.value() : this.code;
    }
}
