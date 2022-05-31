package com.lu.jl18024_bak_d.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestResponse implements Serializable {
    private Long sqlStoreTime;
    private Long sqlReadTime;
    private Long mongoStoreTime;
    private Long mongoReadTime;
}
