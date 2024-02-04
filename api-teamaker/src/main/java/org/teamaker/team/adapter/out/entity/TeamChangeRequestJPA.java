package org.teamaker.team.adapter.out.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamaker.developer.adapter.out.entity.DeveloperJPA;
import org.teamaker.project.adapter.out.persistence.ProjectJPA;
import org.teamaker.team.domain.TeamRequestStatus;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamChangeRequestJPA {
    @EmbeddedId
    private TeamChangeRequestPK id;

    @ManyToOne
    @MapsId("currentProjectId")
    @JoinColumn(name = "current_project_id", referencedColumnName = "id")
    private ProjectJPA currentProject;

    @ManyToOne
    @MapsId("developerId")
    @JoinColumn(name = "developer_id", referencedColumnName = "id")
    private DeveloperJPA developer;

    @ManyToOne
    @JoinColumn(name = "requested_project_id", referencedColumnName = "id")
    private ProjectJPA requestedProject;

    @Nonnull
    private LocalDate date;

    @Nonnull
    @Enumerated(EnumType.STRING)
    private TeamRequestStatus status;
}
