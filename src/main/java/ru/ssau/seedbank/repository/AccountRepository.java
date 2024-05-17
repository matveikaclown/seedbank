package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountByLogin(String login);

}
