package com.geddit.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    private String username;
    @NotNull
    private String password;

    @Column
    private String profileImageUrl;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable
    private Set<AppUser> following = new HashSet<>();

    @ManyToMany
    @JoinTable
    private Set<AppUser> followers = new HashSet<>();

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
