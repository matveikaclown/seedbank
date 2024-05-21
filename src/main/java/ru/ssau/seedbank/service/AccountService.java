package ru.ssau.seedbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.configuration.websecurity.SeedBankUserDetails;
import ru.ssau.seedbank.model.Account;
import ru.ssau.seedbank.repository.AccountRepository;
import ru.ssau.seedbank.repository.RoleRepository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    private static String getGreeting() {
        LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(5, 59)) && now.isBefore(LocalTime.NOON)) {
            return "Доброе утро";
        } else if (now.isAfter(LocalTime.of(11, 59)) && now.isBefore(LocalTime.of(18, 0))) {
            return "Добрый день";
        } else if (now.isAfter(LocalTime.of(17, 59)) && now.isBefore(LocalTime.of(22, 0))) {
            return "Добрый вечер";
        } else {
            return "Доброй ночи";
        }
    }

    public HashMap<String, String> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeedBankUserDetails userDetails = (SeedBankUserDetails) authentication.getPrincipal();
        HashMap<String, String> map = new HashMap<>();
        map.put("greeting", getGreeting());
        map.put("firstName", userDetails.getFirstName());
        return map;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeedBankUserDetails userDetails = (SeedBankUserDetails) authentication.getPrincipal();
        return userDetails.isAdmin();
    }

    public List<Account> findAllAdmins() {
        return accountRepository.findAccountsByRole_RoleIdAndActiveIsTrue(1);
    }

    public List<Account> findAllUsers() {
        return accountRepository.findAccountsByRole_RoleIdAndActiveIsTrue(2);
    }

    public void deleteAccount(Integer id) throws Exception {
        if (!accountRepository.findById(id).orElseThrow(() -> new Exception("Trying to delete ADMIN user."))
                .getRole().getNameOfRole().equals("ADMIN")) {
            Account account = accountRepository.findAccountByAccountId(id);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(6);
            account.setPassword(encoder.encode(account.getPassword()));
            account.setActive(false);
            accountRepository.save(account);
        }
    }

    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeedBankUserDetails userDetails = (SeedBankUserDetails) authentication.getPrincipal();
        return userDetails.getAccountId();
    }

    public Account findUserById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public void saveUser(Integer id, String firstName, String lastName, String patronymic, String login) {
        Account user = accountRepository.findAccountByAccountId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setLogin(login);
        accountRepository.save(user);
    }

    public void saveUser(Integer id, String pswrd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        Account user = accountRepository.findAccountByAccountId(id);
        user.setPassword(encoder.encode(pswrd));
        accountRepository.save(user);
    }

    public void addNewUser(String firstName, String lastName, String patronymic, String login, String pswrd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        Account user = new Account();
        user.setRole(roleRepository.findRoleByRoleId(2));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setLogin(login);
        user.setPassword(encoder.encode(pswrd));
        user.setActive(true);
        accountRepository.save(user);
    }
}
