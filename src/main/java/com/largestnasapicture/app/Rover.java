package com.largestnasapicture.app;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Rover {
    private int id;
    private String name;
    private LocalDate landing_date;
    private LocalDate launch_date;
    private String status;
    private int max_sol;
    private LocalDate max_date;
    private int total_photos;
    private List<Cameras> cameras;
}
