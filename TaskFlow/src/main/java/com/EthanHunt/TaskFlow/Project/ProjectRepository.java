package com.EthanHunt.TaskFlow.Project;

import com.EthanHunt.TaskFlow.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByUser(User user);

//    Project findById(Long id);

    Project findByTitle(String title);

    Project findByUserAndTitle(User user,String title);

    Project findByUserAndId(User user, Long id);
}
