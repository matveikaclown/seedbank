package ru.ssau.seedbank.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.ssau.seedbank.configuration.websecurity.SeedBankUserDetails;
import ru.ssau.seedbank.model.Account;
import ru.ssau.seedbank.model.Role;
import ru.ssau.seedbank.repository.AccountRepository;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    @Test
    public void test_is_admin_identification() {
        Account adminAccount = new Account();
        Role adminRole = new Role();
        adminRole.setNameOfRole("ADMIN");
        adminAccount.setRole(adminRole);
        SeedBankUserDetails userDetails = new SeedBankUserDetails(adminAccount);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccountService service = new AccountService(null, null);
        assertTrue(service.isAdmin());
    }

    @Test
    public void test_delete_admin_account_exception() {
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        Mockito.when(accountRepository.findById(1)).thenReturn(Optional.of(new Account()));
        AccountService service = new AccountService(accountRepository, null);
        assertThrows(Exception.class, () -> service.deleteAccount(1));
    }

    @Test
    public void test_find_user_by_id_not_found() {
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        Mockito.when(accountRepository.findById(999)).thenReturn(Optional.empty());
        AccountService service = new AccountService(accountRepository, null);
        assertThrows(UsernameNotFoundException.class, () -> service.findUserById(999));
    }

}
