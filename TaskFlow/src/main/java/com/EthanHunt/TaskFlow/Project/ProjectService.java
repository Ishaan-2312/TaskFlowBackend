package com.EthanHunt.TaskFlow.Project;

import com.EthanHunt.TaskFlow.RequestDTO.ProjectUpdateRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.ProjectFetchResponse;
import com.EthanHunt.TaskFlow.ResponseDTO.ProjectUpdateResponse;
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

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public @Nullable ProjectUpdateResponse addProject(ProjectUpdateRequest projectUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null){
            return ProjectUpdateResponse.builder().success(false).build();
        }
        Project existing = projectRepository.findByUserAndTitle(user,projectUpdateRequest.getTitle());
        if(existing!=null)return ProjectUpdateResponse.builder().success(false).message("Title already exists").build();
        Project newProject = Project.builder()
                .title(projectUpdateRequest.getTitle())
                .description(projectUpdateRequest.getDescription())
                .user(user)
                .build();

        Project savedProject = projectRepository.save(newProject);
        return ProjectUpdateResponse.builder()
                .projectId(savedProject.getId())
                .title(savedProject.getTitle())
                .description(savedProject.getDescription())
                .success(true).build();


    }

    @Transactional
    public @Nullable ProjectFetchResponse getAllProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null)return ProjectFetchResponse.builder().found(false).message("User does not exist").build();
        List<Project> projects = projectRepository.findByUser(user);
        if(projects!=null && !projects.isEmpty()){
            return ProjectFetchResponse.builder().projects(projects).found(true).build();
        }
        return ProjectFetchResponse.builder().projects(new ArrayList<>()).found(false).build();

    }

    @Transactional
    public @Nullable ProjectFetchResponse getProjectById(String title ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null)return ProjectFetchResponse.builder().found(false).message("User does not exist").build();
        Project project = projectRepository.findByUserAndTitle(user,title);
        List<Project> result = new ArrayList<>();
        result.add(project);
        if(project!=null ){
            return ProjectFetchResponse.builder().projects(result).found(true).build();
        }
        return ProjectFetchResponse.builder().projects(new ArrayList<>()).found(false).build();
    }


    @Transactional
    public ProjectUpdateResponse deleteByTitle(String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null)return ProjectUpdateResponse.builder().success(false).build();
        Project project = projectRepository.findByUserAndTitle(user,title);

        if(project!=null){
            projectRepository.deleteById(project.getId());
            return ProjectUpdateResponse.builder()
                    .projectId(project.getId())
                    .title(project.getTitle())
                    .description(project.getDescription())
                    .userEmail(project.getUser().getEmail())
                    .build();
        }
        return ProjectUpdateResponse.builder().success(false).build();
    }

    @Transactional
    public @Nullable ProjectUpdateResponse updateProject(ProjectUpdateRequest projectUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(user==null){
            return ProjectUpdateResponse.builder().success(false).build();
        }
        Project existing = projectRepository.findByUserAndId(user,projectUpdateRequest.getId());
        if(existing==null)return ProjectUpdateResponse.builder().success(false).message("No such Project Exists").build();
        existing.setDescription(projectUpdateRequest.getDescription());
        existing.setTitle(projectUpdateRequest.getTitle());
        projectRepository.save(existing);
        return ProjectUpdateResponse.builder().title(existing.getTitle()).description(existing.getDescription()).projectId(existing.getId()).success(true).build();

    }
}
