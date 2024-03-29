package org.teamaker.team.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.teamaker.developer.domain.Developer;
import org.teamaker.project.domain.Project;
import org.teamaker.project.domain.ProjectPriority;
import org.teamaker.project.domain.ProjectStatus;
import org.teamaker.technology.domain.Technology;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    @Nested
    @DisplayName("getTeamProblems")
    class GetTeamProblems {
        @Test
        public void testGetTeamProblems_NotEnoughDevs() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("Not enough developers in team."));
        }

        @Test
        public void testGetTeamProblems_notEnoughDevsForATechnology() {
            Map<Technology, Integer> technologiesForProject = Map.of(new Technology("id", "name"), 2);
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), technologiesForProject);
            ArrayList<Developer> developers = new ArrayList<>();
            Developer dev1 = new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null);
            dev1.setSkills(List.of(new Technology("id", "name")));
            developers.add(dev1);

            List<Technology> technologies = new ArrayList<>();
            technologies.add(new Technology("id", "name"));


            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("Not enough developers in team."));
        }

        @Test
        public void testGetTeamProblems_EnoughDevs() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev3", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("Not enough developers in team."));
        }

        @Test
        public void testGetTeamProblems_TooManyDevs() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev3", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev4", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev5", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev6", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev7", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev8", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev9", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("Too many developers in team."));
        }

        @Test
        public void testGetTeamProblems_NotTooManyDevs() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev3", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("Too many developers in team."));
        }

        @Test
        public void testGetTeamProblems_JuniorWithoutExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Tom", "t@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("Cannot have a junior without an expert in a team."));
        }

        @Test
        public void testGetTeamProblems_JuniorWithExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Tom", "t@gmail.com", LocalDate.of(2017, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("Cannot have a junior without an expert in a team."));
        }

        @Test
        public void testGetTeamProblems_TooManyJuniors() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Tom", "t@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev3", "Anaelle", "t@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev4", "Jean", "t@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("Cannot have more than 3 juniors in a team."));
        }

        @Test
        public void testGetTeamProblems_NotTooManyJuniors() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Tom", "t@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev3", "Anaelle", "t@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("Cannot have more than 3 juniors in a team."));
        }

        @Test
        public void testGetTeamProblems_LongProjectNoExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("Cannot do a project longer than 6 months without an expert in the team."));
        }

        @Test
        public void testGetTeamProblems_ShortProjectNoExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("Cannot do a project longer than 6 months without an expert in the team."));
        }

        @Test
        public void testGetTeamProblems_LongProjectWithExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2017, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("Cannot do a project longer than 6 months without an expert in the team."));
        }

        @Test
        public void testGetTeamProblems_SmallTeamWithExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Anaelle", "p@gmail.com", LocalDate.of(2017, 1, 1), null));
            developers.add(new Developer("dev3", "Tom", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertTrue(response.contains("An expert cannot be in a team of less than 5 developers unless it is a critical project."));
        }

        @Test
        public void testGetTeamProblems_SmallTeamWithExpertCritical() {
            Project project = new Project("id", "name", "description", ProjectPriority.CRITICAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev2", "Anaelle", "p@gmail.com", LocalDate.of(2017, 1, 1), null));
            developers.add(new Developer("dev3", "Tom", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("An expert cannot be in a team of less than 5 developers unless it is a critical project."));
        }

        @Test
        public void testGetTeamProblems_BigTeamWithExpert() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            ArrayList<Developer> developers = new ArrayList<>();
            developers.add(new Developer("dev1", "Anaelle", "p@gmail.com", LocalDate.of(2017, 1, 1), null));
            developers.add(new Developer("dev2", "Tom", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev3", "Tom", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev4", "Tom", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            developers.add(new Developer("dev5", "Tom", "p@gmail.com", LocalDate.of(2023, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            List<String> response = team.getTeamProblems(project);

            assertFalse(response.contains("An expert cannot be in a team of less than 5 developers unless it is a critical project."));
        }
    }

    @Nested
    @DisplayName("lock")
    class Lock {
        @Test
        public void testLockSuccessful() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            Team team = new Team(project.getProjectId(), getDevelopersForValidTeam(), false);

            team.lock(project);

            assertTrue(team.isLocked());
        }

        @Test
        public void testLockFailBadRules() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            Team team = new Team(project.getProjectId(), new ArrayList<>(), false);

            assertThrows(IllegalStateException.class, () -> team.lock(project));
        }

        @Test
        public void testLockFailWrongProject() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            Team team = new Team("other-id", getDevelopersForValidTeam(), false);

            assertThrows(IllegalArgumentException.class, () -> team.lock(project));
        }
    }

    @Nested
    @DisplayName("removeDeveloper")
    class RemoveDeveloper {
        @Test
        public void testRemoveDeveloper_SuccessLocked() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = getDevelopersForValidTeam();
            developers.add(new Developer("removed", "i'll be removed", "helpme@gmail.com", LocalDate.of(2022, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, true);

            team.removeDeveloperById("removed", project, false);

            assertEquals(3, team.getDevelopers().size());
        }
        @Test
        public void testRemoveDeveloper_SuccessNotLocked() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = new ArrayList<>();
            developers.add(new Developer("safe", "i'm safe", "cheh@gmail.com", LocalDate.of(2022, 1, 1), null));
            developers.add(new Developer("removed", "i'll be removed", "helpme@gmail.com", LocalDate.of(2022, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, false);

            team.removeDeveloperById("removed", project, false);

            assertEquals(1, team.getDevelopers().size());
        }

        @Test
        public void testRemoveDeveloper_Failure() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = getDevelopersForValidTeam();
            Team team = new Team(project.getProjectId(), developers, true);

            List<String> response = team.removeDeveloperById("dev1", project, false);

            assertFalse(response.isEmpty());
            assertEquals(3, team.getDevelopers().size());
        }

        @Test
        public void testRemoveDeveloper_NoRemove() {
            Project project = new Project("id", "name", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = getDevelopersForValidTeam();
            developers.add(new Developer("removed", "i'll be removed", "helpme@gmail.com", LocalDate.of(2022, 1, 1), null));
            Team team = new Team(project.getProjectId(), developers, true);

            team.removeDeveloperById("removed", project, true);

            assertEquals(4, team.getDevelopers().size());
        }
    }

    @Nested
    @DisplayName("addDeveloperToTeam")
    class AddDeveloperToTeam {

        @Test
        public void testAddDeveloper_SuccessLocked() {
            Project project = new Project("id", "Test Project", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = getDevelopersForValidTeam();
            Team team = new Team(project.getProjectId(), developers, true);
            Developer newDeveloper = new Developer("new", "New Developer", "newdev@gmail.com", LocalDate.of(2020, 1, 1), null);

            List<String> errors = team.addDeveloper(newDeveloper, project, false);

            assertNull(errors);
            assertTrue(team.getDevelopers().contains(newDeveloper));
            assertEquals(4, team.getDevelopers().size());

        }

        @Test
        public void testAddDeveloper_SuccessNotLocked() {
            Project project = new Project("id", "Test Project", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = new ArrayList<>();
            Team team = new Team(project.getProjectId(), developers, false);
            Developer newDeveloper = new Developer("new", "New Developer", "newdev@gmail.com", LocalDate.of(2023, 1, 1), null);

            team.addDeveloper(newDeveloper, project, false);

            assertTrue(team.getDevelopers().contains(newDeveloper));
            assertEquals(1, team.getDevelopers().size());
        }

        @Test
        public void testAddDeveloper_Failure() {
            Project project = new Project("id", "Test Project", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = getDevelopersForValidTeam();
            Team team = new Team(project.getProjectId(), developers, true);
            Developer newDeveloper = new Developer("dev1", "Existing Developer", "existingdev@gmail.com", LocalDate.of(2023, 1, 1), null); // assuming dev1 breaks rules

            List<String> response = team.addDeveloper(newDeveloper, project, false);

            assertFalse(response.isEmpty());
            assertFalse(team.getDevelopers().contains(newDeveloper));
            assertEquals(3, team.getDevelopers().size());
        }

        @Test
        public void testAddDeveloper_NoAdd() {
            Project project = new Project("id", "Test Project", "description", ProjectPriority.NORMAL, ProjectStatus.PENDING, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10), new Team("projectId", new ArrayList<>(), false), Map.of());
            List<Developer> developers = getDevelopersForValidTeam();
            Team team = new Team(project.getProjectId(), developers, true);
            Developer newDeveloper = new Developer("new", "New Developer", "newdev@gmail.com", LocalDate.of(2020, 1, 1), null);

            List<String> errors = team.addDeveloper(newDeveloper, project, true);

            assertNull(errors);
            assertFalse(team.getDevelopers().contains(newDeveloper));
            assertEquals(3, team.getDevelopers().size());
        }
    }


    private List<Developer> getDevelopersForValidTeam() {
        ArrayList<Developer> developers = new ArrayList<>();

        developers.add(new Developer("dev1", "Paul", "p@gmail.com", LocalDate.of(2019, 1, 1), null));
        developers.add(new Developer("dev2", "Tom", "p@gmail.com", LocalDate.of(2019, 1, 1), null));
        developers.add(new Developer("dev3", "Anaelle", "p@gmail.com", LocalDate.of(2019, 1, 1), null));

        return developers;
    }
}