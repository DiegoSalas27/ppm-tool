package ppmtool.com.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppmtool.com.ppmtool.exceptions.ProjectIdException;
import ppmtool.com.ppmtool.models.Project;
import ppmtool.com.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project obj) {
        try {
            obj.setProjectIdentifier(obj.getProjectIdentifier().toUpperCase());
            return projectRepository.save(obj);
        } catch(Exception e) {
            throw new ProjectIdException("Project Id '" + obj.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) throw new ProjectIdException("Project Id doesn't exists");

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null) throw new ProjectIdException("Cannot delete project with ID '" + projectId + "'. This project does not exist");

        projectRepository.delete(project);
    }
}
