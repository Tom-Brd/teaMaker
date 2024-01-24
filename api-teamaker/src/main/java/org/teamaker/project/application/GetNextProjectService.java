package org.teamaker.project.application;

import org.springframework.stereotype.Component;
import org.teamaker.project.application.port.in.getNextProject.GetNextProjectResponse;
import org.teamaker.project.application.port.in.getNextProject.GetNextProject;
import org.teamaker.project.application.port.out.findNextProject.FindNextProjectPort;

import java.util.NoSuchElementException;

@Component
public class GetNextProjectService implements GetNextProject {
    private final FindNextProjectPort findNextProject;

    public GetNextProjectService(FindNextProjectPort findNextProject) {
        this.findNextProject = findNextProject;
    }

    @Override
    public GetNextProjectResponse.Response getNextProject() {
        try {
            return new GetNextProjectResponse.SuccessResponse(findNextProject.findNextProject().toResponse());
        } catch(NoSuchElementException exception) {
            return new GetNextProjectResponse.ErrorResponse(exception.getMessage());
        }
    }
}
