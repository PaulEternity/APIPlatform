package com.paul.paulapiclientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RandomWallpaperParams implements Serializable {
    private static final long serialVersionUID = -87300540799904152L;
    private String lx;
    private String method;
}
