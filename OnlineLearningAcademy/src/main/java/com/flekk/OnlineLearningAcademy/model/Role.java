package com.flekk.OnlineLearningAcademy.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.USERS_EDIT, Permission.AREAS_OF_STUDY_EDIT, Permission.REPORT_ADMIN)),
    TEACHER(Set.of(Permission.COURSES_EDIT, Permission.AREAS_OF_STUDY_READ, Permission.STUDENTS_READ, Permission.LESSONS_EDIT, Permission.REPORT_TEACHER)),
    STUDENT(Set.of(Permission.COURSES_ENROLL, Permission.AREAS_OF_STUDY_READ, Permission.LESSONS_ENROLL, Permission.REPORT_STUDENT));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
