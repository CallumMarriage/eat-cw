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
import java.io.PrintWriter;


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

    @GetMapping("/registration")
    public String getRegistration(){
        return "registration";
    }

    @PostMapping("/login")
    public String postLogin(
            HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");

        if (!loginService.checkIfInUse(username)) {
            log.info("he1y");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeMessageToResponse(response, "<error>\n" +
                    "<code>1" +
                    "</code>\n" +
                    "<message>The username does not exist</message>\n" +
                    "</error>");
            return "index";
        }

        if (loginService.validate(username, request.getParameter("password"))) {
            log.info("he2y");
            request.getSession().setAttribute("isLoggedIn", "true");
            request.getSession().setAttribute("username", username);
            return "redirect:/viewTimetable";
        } else {
            log.info("hey3");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeMessageToResponse(response, "<error>\n" +
                    "<code>2" +
                    "</code>\n" +
                    "<message>The password was incorrect</message>\n" +
                    "</error>");
            return "index";
        }
    }

    @GetMapping("/getClientID")
    public String getClientId(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");

        Integer clientID = loginService.getClientIdByUsername(username);
        if (clientID == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "viewTimetable";
        }
        writeMessageToResponse(response, clientID.toString());

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
            writeMessageToResponse(response, "<errorMessage>The username is already in use</errorMessage><errorType>3</errorType>");
            return "index";
        }

        Client client = Client.builder()
                .password(password)
                .username(username)
                .build();


        loginService.addNewUser(client);
        return "redirect:/viewSelection";
    }

    private void writeMessageToResponse(HttpServletResponse response, String message) {
        response.setContentType("application/xml, charset=\"UTF-8\"");
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<?xml version='1.0' encoding='UTF-8'?> \n" + message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
