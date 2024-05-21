package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByRoleId(Integer id);

}
