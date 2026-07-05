package com.EthanHunt.TaskFlow.RequestDTO;

import com.EthanHunt.TaskFlow.Task.Priority;
import com.EthanHunt.TaskFlow.Task.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private Long projectId;

    private String title;

    private String description;

    private LocalDate dueDate;

    private Priority priority;

    private Status status;

    private Long taskId;
}
