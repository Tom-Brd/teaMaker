package org.teamaker.project.application.port.in.submitProject;

import org.teamaker.project.application.port.dto.ProjectResponse;

public interface SubmitProjectUseCase {
    ProjectResponse submitProject(SubmitProjectCommand command);
}