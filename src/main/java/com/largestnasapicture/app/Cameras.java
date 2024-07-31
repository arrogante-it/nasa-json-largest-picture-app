package com.largestnasapicture.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cameras {
    private String name;
    @JsonProperty("full_name")
    private String fullName;
}
