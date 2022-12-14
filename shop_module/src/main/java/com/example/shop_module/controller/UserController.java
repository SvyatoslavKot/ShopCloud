package com.example.shop_module.controller;

import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.mq.ProducerShopClient;
import com.example.shop_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ProducerShopClient producerShopClientService;
    @Autowired
    public UserController(UserService userService, ProducerShopClient producerShopClientService) {
        this.userService = userService;
        this.producerShopClientService = producerShopClientService;
    }


    @GetMapping("/list")
    public String userList (Model model){
        model.addAttribute("users", userService.findAllUserDto());
        return "userList";
    }
    @PreAuthorize("isAuthenticated() and #mail == authentication.principal.username")
    @GetMapping("/{mail}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("mail") String mail) {
        User user = userService.finByMail(mail);
        return user.getRole().name();
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("dto", new UserDTO());
        return  "registration";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("dto") UserDTO userDTO, Model model) {
        System.out.println(userDTO.getMail() + " " + userDTO.getPassword());
        ResponseEntity response = userService.registrationClient(userDTO);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return "redirect:/login";
        }
        System.out.println("error ->" + response.getBody());
        return "redirect:/user/new";
    }


    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null) {
            throw new RuntimeException("You are not authorize!");
        }
        UserDTO userDTO =  producerShopClientService.getClientByMail(principal.getName());
       /* User user = userService.finByMail(principal.getName());

        UserDTO dto  = UserDTO.builder()
                .firstName(user.getName())
                .mail(user.getMail())
                .build();

        */
        model.addAttribute("user", userDTO);
        return "profile";

    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getMail())){
            throw new RuntimeException("You are not authorize!");
        }
        userService.updateProfile(dto);
        return "redirect:/user/profile";
    }
}
