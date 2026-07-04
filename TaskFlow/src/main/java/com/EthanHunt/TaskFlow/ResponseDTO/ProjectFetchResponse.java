package com.EthanHunt.TaskFlow.ResponseDTO;

import com.EthanHunt.TaskFlow.Project.Project;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectFetchResponse {
    private List<Project> projects;
    private  boolean found;
    private String message;
}
