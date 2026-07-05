package com.EthanHunt.TaskFlow.Task;

import com.EthanHunt.TaskFlow.RequestDTO.TaskUpdateRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.TaskFetchResponse;
import com.EthanHunt.TaskFlow.ResponseDTO.TaskUpdateResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("addTask")
    public ResponseEntity<TaskUpdateResponse> addTask(@RequestBody TaskUpdateRequest taskUpdateRequest){
        return ResponseEntity.ok(taskService.addTask(taskUpdateRequest));

    }

    @GetMapping("getTasks")
    public ResponseEntity<List<TaskFetchResponse>> getTasks(@RequestParam Long projectId) {
        return ResponseEntity.ok(taskService.fetchTasks(projectId));
    }

    @DeleteMapping("deleteTask")
    public ResponseEntity<TaskUpdateResponse> deleteTask(@RequestParam Long projectId,@RequestParam Long taskId){
        return ResponseEntity.ok(taskService.deleteTask(projectId,taskId));
    }

    @PutMapping("editTask")
    public ResponseEntity<TaskUpdateResponse> editTask(@RequestBody TaskUpdateRequest taskUpdateRequest){
        return ResponseEntity.ok(taskService.editTask(taskUpdateRequest));
    }


}
