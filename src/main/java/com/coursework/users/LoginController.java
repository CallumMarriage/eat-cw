package com.coursework.users;

import com.coursework.common.ResponseBuilder;
import com.coursework.common.SessionHandler;
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

    private final ResponseBuilder responseBuilder;

    private final SessionHandler sessionHandler;

    public LoginController(LoginService loginService, ResponseBuilder responseBuilder, SessionHandler sessionHandler) {
        this.loginService = loginService;
        this.responseBuilder = responseBuilder;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");

        if (!loginService.checkIfInUse(username)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBuilder.writeErrorMessageToResponse(response, "The username does not exist", 1);
            return "index";
        }

        if (loginService.validate(username, request.getParameter("password"))) {
            sessionHandler.setUserAsLoggedinInSession(request, username);
            return "redirect:/viewTimetable";
        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBuilder.writeErrorMessageToResponse(response, "The password was incorrect", 2);
            return "index";
        }
    }

    @GetMapping("/getClientID")
    public String getClientId(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");

        Integer clientID = loginService.getClientIdByUsername(username);
        if (clientID == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBuilder.writeErrorMessageToResponse(response, "No clientID for that username", 4);
            return "viewTimetable";
        }
        responseBuilder.writeMessageToResponse(response, "<clientID>" + clientID.toString() + "</clientID>");
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
            responseBuilder.writeErrorMessageToResponse(response, "The username is already in use", 3);
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
