package ru.gb.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
