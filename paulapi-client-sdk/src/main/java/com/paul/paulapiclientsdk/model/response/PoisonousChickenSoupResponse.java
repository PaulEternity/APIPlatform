package com.paul.paulapiclientsdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PoisonousChickenSoupResponse extends ResultResponse{
    private static final long serialVersionUID = 1L;
    private String text;
}