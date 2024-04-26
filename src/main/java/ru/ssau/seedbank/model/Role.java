package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;     // ID роли

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<Account> accounts;

    private String nameOfRole;  // название роли

}
