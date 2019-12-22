package com.coursework.users;

import com.coursework.users.model.Client;
import com.coursework.users.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by callummarriage on 08/12/2019.
 */
@Controller
@Slf4j
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public String postLogin(
            HttpServletRequest request) {


        String username = loginService.validate(request.getParameter("username"), request.getParameter("password"));
        if (username != null) {
            request.getSession().setAttribute("isLoggedIn", "true");
            request.getSession().setAttribute("username", username);
            return "redirect:/viewTimetable";
        } else {
            request.getSession().setAttribute("loginError", "Error logging in.");
            return "redirect:/";
        }
    }

    @GetMapping("/getClientID")
    public String getClientId(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");

        Integer clientID = loginService.getClientIdByUsername(username);
        if(clientID == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "viewTimetable";
        }
        try {
            response.getWriter().write("<clientID>" + clientID + "<clientID>");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "viewTimetable";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String postRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("newUsername");
        String password = request.getParameter("newPassword");

        if (loginService.checkIfInUse(username)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("The username you have entered is already in use");
            response.setContentType("plain/text");
            response.getWriter().flush();
            return "index";
        }

        Client client = Client.builder()
                .password(password)
                .username(username)
                .build();


        loginService.addNewUser(client);
        return "redirect:/viewSelection";

    }
}
