package com.paul.apiPlatform.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;
@Data
public class InterfaceInfoInvokeRequest implements Serializable {


    /**
     * 主键
     */
    private Long id;


    private String userResponseParams;

}
