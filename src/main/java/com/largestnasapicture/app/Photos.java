package com.largestnasapicture.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Photos {
    private int id;
    private int sol;
    private Camera camera;
    @JsonProperty("img_src")
    private String imgSrc;
    @JsonProperty("earth_date")
    private LocalDate earthDate;
    private Rover rover;
}
