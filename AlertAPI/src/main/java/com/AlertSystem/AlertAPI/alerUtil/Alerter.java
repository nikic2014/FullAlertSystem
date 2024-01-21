package com.AlertSystem.AlertAPI.alerUtil;

import com.AlertSystem.AlertAPI.models.People;
import com.AlertSystem.AlertAPI.models.PeopleInProject;
import com.AlertSystem.AlertAPI.models.Project;
import com.AlertSystem.AlertAPI.services.PeopleInProjectService;
import com.AlertSystem.AlertAPI.services.PeopleService;
import com.AlertSystem.AlertAPI.services.ProjectService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Alerter {
    private final SmsAlert smsAlert;
    private final TelegramAlert telegramAlert;
    private final EmailAlert emailAlert;
    private final ProjectService projectService;
    private final PeopleService peopleService;
    private final PeopleInProjectService peopleInProjectService;

    public Alerter(SmsAlert smsAlert,
                   TelegramAlert telegramAlert,
                   EmailAlert emailAlert,
                   ProjectService projectService,
                   PeopleService peopleService,
                   PeopleInProjectService peopleInProjectService) {
        this.smsAlert = smsAlert;
        this.telegramAlert = telegramAlert;
        this.emailAlert = emailAlert;
        this.projectService = projectService;
        this.peopleService = peopleService;
        this.peopleInProjectService = peopleInProjectService;
    }

    public void sendAlert(int idProject,
                          String alertType,
                          String message,
                          String alertPath,
                          int id) throws Exception {
        Project project = projectService.findByIdProject(idProject);
        String Message = message + "\n" + alertPath;
        List<People> people = List.of(peopleService.getById(id));
        typeToAlert(alertType, project, Message, people);
    }

    public void sendAlert(int idProject,
                          String alertType,
                          String message,
                          String alertPath,
                          String role) throws Exception {
        Project project = projectService.findByIdProject(idProject);
        String Message = message + "\n" + alertPath;
        List<PeopleInProject> peopleInProjectList =
                peopleInProjectService.getByRoleAndProjectId(role, idProject);
        List<People> people = new ArrayList<>();
        for (var i : peopleInProjectList){
            people.add(this.peopleService.getById(i.getPeopleInProjectId().getIdPeople()));
        }
        typeToAlert(alertType, project, Message, people);
    }

    public void typeToAlert(String alertType, Project project,
                            String message, List<People> peopleList) throws Exception {
        switch (alertType){
            case "email":
                EmailAlert(project, message, peopleList);
                break;
            case "sms":
                SmsAlert(project, message, peopleList);
                break;
            case "telegram":
                TelegramAlert(project, message, peopleList);
                break;
            case "telegramAll":
                TelegramInfoAlert(project, message);
                break;
            default:
                System.out.println("Non type of alert");
        }
    }

    public void EmailAlert(Project project,
                           String message,
                           List<People> peopleList) {
        for(var i:peopleList){
            emailAlert.sendEmail(i.getEmail(),
                    "Problem in project" + project.getId(),
                    message);
        }
    }

    public void TelegramAlert(Project project,
                           String message,
                           List<People> peopleList) throws Exception {
        String tgLinksPeople = "";
        for(var i:peopleList){
            tgLinksPeople += i.getTgLink() + " ";
        }
        message = "For: " + tgLinksPeople + "\n" + message;

        telegramAlert.sendMessage(project.getId(), message);
    }

    public void TelegramInfoAlert(Project project,
                              String message) throws Exception {
        telegramAlert.sendMessage(project.getId(), message);
    }

    public void SmsAlert(Project project,
                         String message,
                         List<People> peopleList) throws Exception {
        for(var i:peopleList) {
            smsAlert.sendSms(i.getTelephone(),
                    "Problem in project: " + project.getId() + ".\n"
                            + message);
        }
    }
}
