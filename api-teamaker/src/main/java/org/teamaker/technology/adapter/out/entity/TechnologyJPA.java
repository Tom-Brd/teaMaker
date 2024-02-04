package org.teamaker.technology.adapter.out.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TECHNOLOGY")
public class TechnologyJPA {
    @Id
    private String id;

    @Nonnull
    private String name;

    @OneToMany(mappedBy = "technology")
    private Set<TechnologyRequirementJPA> technologyRequirements = new HashSet<>();

    @OneToMany(mappedBy = "technology")
    private Set<SkillJPA> skills = new HashSet<>();
}
