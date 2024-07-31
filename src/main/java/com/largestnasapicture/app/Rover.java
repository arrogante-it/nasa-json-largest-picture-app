package com.largestnasapicture.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Rover {
    private int id;
    private String name;
    @JsonProperty("landing_date")
    private LocalDate landingDate;
    @JsonProperty("launch_date")
    private LocalDate launchDate;
    private String status;
    @JsonProperty("max_sol")
    private int maxSol;
    @JsonProperty("max_date")
    private LocalDate maxDate;
    @JsonProperty("total_photos")
    private int totalPhotos;
    private List<Cameras> cameras;
}
