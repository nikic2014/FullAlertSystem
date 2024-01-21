package com.AlertSystem.AlertAPI.dto;

public class AlertDTO {
    private int idProject;
    private String alertType;
    private String message;
    private String alertPath;

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlertPath() {
        return alertPath;
    }

    public void setAlertPath(String alertPath) {
        this.alertPath = alertPath;
    }
}
