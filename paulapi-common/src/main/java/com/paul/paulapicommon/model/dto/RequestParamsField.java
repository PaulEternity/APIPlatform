package com.paul.paulapicommon.model.dto;

import lombok.Data;

@Data
public class RequestParamsField {
    private String id;

    private String fieldName;

    private String type;

    private String description;

    private String required;
}
