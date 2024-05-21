package ru.ssau.seedbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.seedbank.model.Account;
import ru.ssau.seedbank.service.AccountService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/user")
public class UserController {

    private final AccountService accountService;

    @Autowired
    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String user(Model model) {
        HashMap<String, String> map = accountService.greeting();
        model.addAttribute("userId", accountService.getUserId());
        model.addAttribute("greeting", map.get("greeting"));
        model.addAttribute("firstName", map.get("firstName"));
        if (accountService.isAdmin()) {
            List<Account> admins = accountService.findAllAdmins();
            List<Account> users = accountService.findAllUsers();
            model.addAttribute("admins", admins);
            model.addAttribute("users", users);
        }
        return "user";
    }

    @PostMapping("/delete/id={id}")
    public String deleteUser(@PathVariable(value = "id") Integer id) {
        try {
            accountService.deleteAccount(id);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/logout";
        }
        return "redirect:/user";
    }

    @GetMapping("/edit/id={id}")
    public String editUser(
            @PathVariable(value = "id") Integer id,
            Model model) {
        List<Account> users = Stream.concat(accountService.findAllUsers().stream(), accountService.findAllAdmins().stream())
                .collect(Collectors.toList());
        Account currentUser = accountService.findUserById(id);
        users.removeIf(user -> user.getAccountId().equals(currentUser.getAccountId())); // Удаление текущего пользователя из списка
        model.addAttribute("user", currentUser);
        model.addAttribute("users", users); // Передача списка пользователей
        return "userEdit";
    }

    @PostMapping("/edit/id={id}/info")
    public String editUserInfo(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "patronymic", required = false) String patronymic,
            @RequestParam(value = "login") String login
    ) {
        accountService.saveUser(id, firstName, lastName, patronymic, login);
        return "redirect:/user";
    }

    @PostMapping("/edit/id={id}/pswrd")
    public String editUserPswrd(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "pswrd") String pswrd
    ) {
        accountService.saveUser(id, pswrd);
        return "redirect:/user";
    }

    @PostMapping("/add")
    public String addUser(
            @RequestParam(value = "newFirstName") String firstName,
            @RequestParam(value = "newLastName", required = false) String lastName,
            @RequestParam(value = "newPatronymic", required = false) String patronymic,
            @RequestParam(value = "newLogin") String login,
            @RequestParam(value = "newPswrd") String pswrd
    ) {
        accountService.addNewUser(firstName, lastName, patronymic, login, pswrd);
        return "redirect:/user";
    }

}
