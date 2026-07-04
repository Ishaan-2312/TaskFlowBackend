package com.EthanHunt.TaskFlow.RequestDTO;

import com.EthanHunt.TaskFlow.Task.Task;
import lombok.Data;

import java.util.List;

@Data
public class ProjectUpdateRequest {
    private String title;
    private Long id;
    private String description;

}
