package ru.ssau.seedbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.configuration.websecurity.SeedBankUserDetails;
import ru.ssau.seedbank.model.Account;
import ru.ssau.seedbank.repository.AccountRepository;

import java.util.Optional;

@Service
public class SeedBankUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findAccountByLogin(username);
        return account.map(SeedBankUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

}
