package com.EthanHunt.TaskFlow.ResponseDTO;

import com.EthanHunt.TaskFlow.Task.Priority;
import com.EthanHunt.TaskFlow.Task.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskUpdateResponse {
    private Long taskId;

    private Long projectId;

    private String title;

    private String description;

    private Priority priority;

    private Status status;

    private boolean success;

    private String message;
    private LocalDate dueDate;
}

