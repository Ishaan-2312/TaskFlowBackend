package com.EthanHunt.TaskFlow.ResponseDTO;

import com.EthanHunt.TaskFlow.Task.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectUpdateResponse {
    private Long projectId;
    private String title;
    private String description;
    private List<Task> tasks;
    private String userEmail;
    private Boolean success;
    private String message;
}
