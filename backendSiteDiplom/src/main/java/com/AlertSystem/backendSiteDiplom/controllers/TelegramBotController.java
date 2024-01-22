package com.AlertSystem.backendSiteDiplom.controllers;

import com.AlertSystem.backendSiteDiplom.dto.AuthBotDTO;
import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.models.PeopleInProject;
import com.AlertSystem.backendSiteDiplom.models.Project;
import com.AlertSystem.backendSiteDiplom.services.PeopleInProjectService;
import com.AlertSystem.backendSiteDiplom.services.PeopleService;
import com.AlertSystem.backendSiteDiplom.services.ProjectService;
import com.AlertSystem.backendSiteDiplom.util.MyLogger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bot")
public class TelegramBotController {
    private final AuthenticationManager authenticationManager;
    private final ProjectService projectService;
    private final PeopleService peopleService;
    private final PeopleInProjectService peopleInProjectService;
    private final MyLogger myLogger;

    public TelegramBotController(AuthenticationManager authenticationManager,
                                 ProjectService projectService,
                                 PeopleService peopleService,
                                 PeopleInProjectService peopleInProjectService, MyLogger myLogger) {
        this.authenticationManager = authenticationManager;
        this.projectService = projectService;
        this.peopleService = peopleService;
        this.peopleInProjectService = peopleInProjectService;
        this.myLogger = myLogger;
    }

    public boolean haveRool(String username, String id) {
        People people = peopleService.getByLogin(username);
        List<PeopleInProject> peopleInProjects =
                this.peopleInProjectService.findPeopleByProjectId(Integer.parseInt(id));

        for(var i:peopleInProjects){
            if (i.getPeopleInProjectId().getIdPeople() == people.getId())
                return true;
        }

        return false;
    }

    @PostMapping("/authProject")
    public Map authProject(@RequestBody AuthBotDTO authBotDTO){
        myLogger.sendInfo("Пользователь " + authBotDTO.getLogin() +
                " пытается авторизоваться в проекте " + authBotDTO.getIdProject());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authBotDTO.getLogin(),
                        authBotDTO.getPassword());
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (!haveRool(authBotDTO.getLogin(), authBotDTO.getIdProject())) {
                myLogger.sendWarn("Пользователь " + authBotDTO.getLogin() +
                        " не имея прав, пытается войти в проект " + authBotDTO.getIdProject());

                return Map.of("status", "У пользователя нет доступа к " +
                        "данному проекту");
            }

            Project project =
                    this.projectService.findByIdProject(Integer.parseInt(authBotDTO.getIdProject()));
            project.setTelegramLink(authBotDTO.getChatId());
            projectService.saveProject(project);

            return Map.of("status", "success");
        }
        catch (Exception exception) {
            myLogger.sendWarn("У пользователя " + authBotDTO.getLogin() +
                    " возникли проблемы с авторизацией");
            return Map.of("status", "Неправильный логин или пароль");
        }
    }
}
