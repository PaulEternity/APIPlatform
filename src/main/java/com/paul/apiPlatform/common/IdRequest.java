package com.paul.apiPlatform.common;

import lombok.Data;

import javax.annotation.Resource;
import java.io.Serializable;

@Data
public class IdRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

}
