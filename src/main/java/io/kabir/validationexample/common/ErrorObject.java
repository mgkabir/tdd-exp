package io.kabir.validationexample.common;

import com.fasterxml.jackson.annotation.JsonProperty;

class ErrorObject {

    @JsonProperty("code")
    private String code;

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("title")
    private String title;

    @JsonProperty("detail")
    private String detail;

    public ErrorObject() {
    }

    public ErrorObject(String code, int statusCode,String title, String detail) {
        this.code = code;
        this.statusCode = statusCode;
        this.title = title;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
