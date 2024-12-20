package com.paul.paulapiclientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class IpInfoParams implements Serializable {
    private static final long serialVersionUID = 1351620053294063854L;
    private String ip;
}
