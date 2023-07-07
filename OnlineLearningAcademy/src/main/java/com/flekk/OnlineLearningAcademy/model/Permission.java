package com.flekk.OnlineLearningAcademy.model;

public enum Permission {
    COURSES_ENROLL("courses:enroll"),
    COURSES_EDIT("courses:edit"),
    USERS_EDIT("users:edit"),
    STUDENTS_READ("students:read"),
    AREAS_OF_STUDY_READ("areaOfStudy:read"),
    AREAS_OF_STUDY_EDIT("areaOfStudy:edit"),
    LESSONS_EDIT("lessons:edit"),
    LESSONS_ENROLL("lessons:enroll"),
    REPORT_ADMIN("report:admin"),
    REPORT_TEACHER("report:teacher"),
    REPORT_STUDENT("report:student");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
