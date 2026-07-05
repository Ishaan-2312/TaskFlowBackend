package com.EthanHunt.TaskFlow.RequestDTO;


import lombok.Data;


@Data
public class ProjectUpdateRequest {
    private String title;
    private Long id;
    private String description;

}
