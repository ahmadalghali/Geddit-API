package com.geddit.persistence.entity;

import com.geddit.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    private String email;
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

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
