package com.largestnasapicture.app;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Photos {
    private int id;
    private int sol;
    private Camera camera;
    private String img_src;
    private LocalDate earth_date;
    private Rover rover;
}
