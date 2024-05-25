package ru.ssau.seedbank.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;
import ru.ssau.seedbank.model.Account;
import ru.ssau.seedbank.repository.AccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SeedBankUserDetailsServiceTest {

    @Test
    public void test_user_details_loaded_successfully() {
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        SeedBankUserDetailsService service = new SeedBankUserDetailsService();
        ReflectionTestUtils.setField(service, "accountRepository", mockRepo);
        Account account = new Account();
        account.setLogin("existingUser");
        Mockito.when(mockRepo.findAccountByLogin("existingUser")).thenReturn(Optional.of(account));

        UserDetails userDetails = service.loadUserByUsername("existingUser");

        assertNotNull(userDetails);
    }

    @Test
    public void test_correct_user_details_returned() {
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        SeedBankUserDetailsService service = new SeedBankUserDetailsService();
        ReflectionTestUtils.setField(service, "accountRepository", mockRepo);
        Account account = new Account();
        account.setLogin("validUser");
        Mockito.when(mockRepo.findAccountByLogin("validUser")).thenReturn(Optional.of(account));

        UserDetails userDetails = service.loadUserByUsername("validUser");

        assertEquals("validUser", userDetails.getUsername());
    }

    @Test
    public void test_username_not_found_exception_thrown() {
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        SeedBankUserDetailsService service = new SeedBankUserDetailsService();
        ReflectionTestUtils.setField(service, "accountRepository", mockRepo);
        Mockito.when(mockRepo.findAccountByLogin("nonexistentUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("nonexistentUser");
        });
    }

    @Test
    public void test_exception_message_contains_username() {
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        SeedBankUserDetailsService service = new SeedBankUserDetailsService();
        ReflectionTestUtils.setField(service, "accountRepository", mockRepo);
        Mockito.when(mockRepo.findAccountByLogin("unknownUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("unknownUser");
        });

        assertTrue(exception.getMessage().contains("unknownUser"));
    }

    @Test
    public void test_handles_special_characters_in_username() {
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        SeedBankUserDetailsService service = new SeedBankUserDetailsService();
        ReflectionTestUtils.setField(service, "accountRepository", mockRepo);
        Account account = new Account();
        account.setLogin("user@name#123");
        Mockito.when(mockRepo.findAccountByLogin("user@name#123")).thenReturn(Optional.of(account));

        UserDetails userDetails = service.loadUserByUsername("user@name#123");

        assertEquals("user@name#123", userDetails.getUsername());
    }

}
