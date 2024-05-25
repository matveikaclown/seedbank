package ru.ssau.seedbank.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import ru.ssau.seedbank.service.AccountService;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    public void test_greeting_added_to_model() {
        Model model = Mockito.mock(Model.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        HashMap<String, String> greetingMap = new HashMap<>();
        greetingMap.put("greeting", "Hello");
        greetingMap.put("firstName", "John");
        Mockito.when(accountService.greeting()).thenReturn(greetingMap);
        Mockito.when(accountService.getUserId()).thenReturn(1);
        Mockito.when(accountService.isAdmin()).thenReturn(false);
        UserController userController = new UserController(accountService);
        String viewName = userController.user(model);
        Mockito.verify(model).addAttribute("greeting", "Hello");
        assertEquals("user", viewName);
    }

    @Test
    public void test_greeting_returns_null_or_empty() {
        Model model = Mockito.mock(Model.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.greeting()).thenReturn(null);
        UserController userController = new UserController(accountService);
        assertThrows(NullPointerException.class, () -> userController.user(model));
    }

    @Test
    public void test_returns_correct_view_name() {
        UserController controller = new UserController(mock(AccountService.class));
        Model model = mock(Model.class);
        assertEquals("userEdit", controller.editUser(1, model));
    }

    @Test
    public void test_handles_nonexistent_user_id() {
        AccountService service = mock(AccountService.class);
        when(service.findUserById(anyInt())).thenThrow(new UsernameNotFoundException("User not found."));
        UserController controller = new UserController(service);
        Model model = mock(Model.class);
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> controller.editUser(999, model));
        assertTrue(exception.getMessage().contains("User not found."));
    }

    @Test
    public void test_empty_users_and_admins_list() {
        AccountService service = mock(AccountService.class);
        when(service.findAllUsers()).thenReturn(Collections.emptyList());
        when(service.findAllAdmins()).thenReturn(Collections.emptyList());
        UserController controller = new UserController(service);
        Model model = mock(Model.class);
        controller.editUser(1, model);
        verify(model).addAttribute(eq("users"), anyList());
    }

    @Test
    public void test_exception_during_user_retrieval() {
        AccountService service = mock(AccountService.class);
        when(service.findUserById(anyInt())).thenThrow(new RuntimeException("Database error"));
        UserController controller = new UserController(service);
        Model model = mock(Model.class);
        Exception exception = assertThrows(RuntimeException.class, () -> controller.editUser(1, model));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void test_redirect_after_add_user() {
        UserController controller = new UserController(mock(AccountService.class));
        String response = controller.addUser("John", "Doe", "Middle", "john.doe", "password123");
        assertEquals("redirect:/user", response);
    }

    @Test
    public void test_add_user_calls_service_with_correct_parameters() {
        AccountService service = mock(AccountService.class);
        UserController controller = new UserController(service);
        controller.addUser("John", "Doe", "Middle", "john.doe", "password123");
        verify(service).addNewUser("John", "Doe", "Middle", "john.doe", "password123");
    }

    @Test
    public void test_add_user_with_empty_optional_parameters() {
        AccountService service = mock(AccountService.class);
        UserController controller = new UserController(service);
        controller.addUser("John", "", "", "john.doe", "password123");
        verify(service).addNewUser("John", "", "", "john.doe", "password123");
    }

    @Test
    public void test_add_user_exception_handling() {
        AccountService service = mock(AccountService.class);
        doThrow(new RuntimeException("Database error")).when(service).addNewUser(anyString(), anyString(), anyString(), anyString(), anyString());
        UserController controller = new UserController(service);
        Exception exception = assertThrows(RuntimeException.class, () -> controller.addUser("John", "Doe", "Middle", "john.doe", "password123"));
        assertEquals("Database error", exception.getMessage());
    }

}
