package com.paul.paulapiclientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class NameParams implements Serializable {
    private static final long serialVersionUID = 5198275554324650097L;
    private String name;
}
