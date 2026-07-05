package com.EthanHunt.TaskFlow.Task;

import com.EthanHunt.TaskFlow.Project.Project;
import com.EthanHunt.TaskFlow.Project.ProjectRepository;
import com.EthanHunt.TaskFlow.RequestDTO.TaskUpdateRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.TaskFetchResponse;
import com.EthanHunt.TaskFlow.ResponseDTO.TaskUpdateResponse;
import com.EthanHunt.TaskFlow.User.User;
import com.EthanHunt.TaskFlow.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public TaskUpdateResponse addTask(TaskUpdateRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if(user == null){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }

        Project project = projectRepository.findByUserAndId(user, request.getProjectId());

        if(project == null){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("Project not found")
                    .build();
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus())
                .dueDate(request.getDueDate())
                .project(project)
                .build();

        Task savedTask = taskRepository.save(task);

        return TaskUpdateResponse.builder()
                .taskId(savedTask.getId())
                .projectId(project.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .priority(savedTask.getPriority())
                .status(savedTask.getStatus())
                .success(true)
                .message("Task created successfully")
                .build();
    }

    public @Nullable List<TaskFetchResponse> fetchTasks(Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if(user == null){
            return List.of(TaskFetchResponse.builder()
                    .success(false)
                    .message("User not found")
                    .build());
        }

        Project project = projectRepository.findByUserAndId(user, projectId);

        if(project == null){
            return List.of(TaskFetchResponse.builder()
                    .success(false)
                    .message("Project not found")
                    .build());
        }
        List<Task> tasks = project.getTasks();
        List<TaskFetchResponse> response = new ArrayList<>();

       for(Task task:tasks){
           response.add(TaskFetchResponse.builder()
                   .taskId(task.getId())
                   .title(task.getTitle())
                   .description(task.getDescription())
                   .priority(task.getPriority())
                   .status(task.getStatus())
                   .dueDate(task.getDueDate())
                   .success(true)
                   .build());

       }
       return response;

    }

    public @Nullable TaskUpdateResponse deleteTask(Long projectId,Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if(user == null){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }

        Project project = projectRepository.findByUserAndId(user, projectId);
        if(project == null){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("Project not found")
                    .build();
        }
        Optional<Task> toDeleteTask = taskRepository.findById(taskId);
        if(toDeleteTask.isEmpty()){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("Task Does Not Exist")
                    .build();

        }
        taskRepository.deleteById(taskId);

        return TaskUpdateResponse.builder()
                .taskId(toDeleteTask.get().getId())
                .success(true)
                .message("Task Deleted successfully")
                .build();


    }

    public @Nullable TaskUpdateResponse editTask(TaskUpdateRequest taskUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if(user == null){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("User not found")
                    .build();
        }


        Optional<Task> existing = taskRepository.findById(taskUpdateRequest.getTaskId());

        if(existing.isEmpty()){
            return TaskUpdateResponse.builder()
                    .success(false)
                    .message("Task not found")
                    .build();
        }

        Task task = existing.get();

        task.setTitle(taskUpdateRequest.getTitle());
        task.setDescription(taskUpdateRequest.getDescription());
        task.setPriority(taskUpdateRequest.getPriority());
        task.setStatus(taskUpdateRequest.getStatus());
        task.setDueDate(taskUpdateRequest.getDueDate());

        Task updatedTask = taskRepository.save(task);

        return TaskUpdateResponse.builder()
                .taskId(updatedTask.getId())
                .status(updatedTask.getStatus())
                .priority(updatedTask.getPriority())
                .description(updatedTask.getDescription())
                .title(updatedTask.getTitle())
                .dueDate(updatedTask.getDueDate())
                .success(true)
                .build();
    }
}
