package com.example.students_project.security;

public enum UserAuthorities {
    READ("READ"),
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String name;

    UserAuthorities(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
