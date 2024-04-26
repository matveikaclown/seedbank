package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;    // ID личного кабинета

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;            // ID роли

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Seed> seeds;

    private String login;         // логин
    private String password;      // пароль
    private String firstName;     // имя
    private String lastName;      // фамилия
    private String patronymic;    // отчество

}
