package com.EthanHunt.TaskFlow.Project;


import com.EthanHunt.TaskFlow.RequestDTO.ProjectUpdateRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.ProjectFetchResponse;
import com.EthanHunt.TaskFlow.ResponseDTO.ProjectUpdateResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/add")
    public ResponseEntity<ProjectUpdateResponse> addProject(@RequestBody ProjectUpdateRequest projectUpdateRequest)throws  Exception{
        return ResponseEntity.ok(projectService.addProject(projectUpdateRequest));

    }

    @GetMapping("/fetchAll")
    public ResponseEntity<ProjectFetchResponse> fetchAllProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/fetchByTitle")
    public ResponseEntity<ProjectFetchResponse> fetchProjectById(@RequestParam("title")String title){
        return ResponseEntity.ok(projectService.getProjectById(title));
    }

    @DeleteMapping("/deleteByProjectTitle")
    public ResponseEntity<ProjectUpdateResponse> deleteProjectByTitle(@RequestParam("title")String title){
        return ResponseEntity.ok(projectService.deleteByTitle(title));
    }

    @PutMapping("/updateProjectByTitle")
    public ResponseEntity<ProjectUpdateResponse> updateProject(@RequestBody ProjectUpdateRequest projectUpdateRequest){
        return ResponseEntity.ok(projectService.updateProject(projectUpdateRequest));
    }


}
