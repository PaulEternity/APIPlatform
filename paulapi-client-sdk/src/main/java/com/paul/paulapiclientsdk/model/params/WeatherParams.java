package com.paul.paulapiclientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class WeatherParams implements Serializable {
    private static final long serialVersionUID = 6607085598077662952L;
    private String ip;
    private String city;
    private String type;
}
