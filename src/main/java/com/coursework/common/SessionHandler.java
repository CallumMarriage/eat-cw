package com.coursework.common;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionHandler {

    private static final String LESSON_CHOICES = "lessonChoices";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public void removeLessonFromSession(HttpServletRequest request, String lessonId){

        if(!checkIfChoicesPresent(request)){
            return;
        }

        List<String> filtered = getChoices(request).stream()
                .filter(choice -> !choice.equals(lessonId))
                .collect(Collectors.toList());

        if(filtered.size() == 0){
            removeLessonsAttributeFromSession(request);
            return;
        }

        request.getSession().setAttribute(LESSON_CHOICES, filtered);
    }

    public String getLessonIdFromSession(HttpServletRequest request){
        return request.getParameter("lessonId");
    }

    public Integer getClientIdFromSession(HttpServletRequest request){
        return Integer.parseInt(request.getParameter("clientId"));
    }

    public void setUserAsLoggedinInSession(HttpServletRequest request, String username){
        request.getSession().setAttribute(IS_LOGGED_IN, "true");
        request.getSession().setAttribute("username", username);
    }

    public Boolean checkIfLoggedIn(HttpServletRequest request) {
        return "true".equals(request.getSession().getAttribute(IS_LOGGED_IN));
    }

    public void createLessonChoicesAttributeInSession(HttpServletRequest request){
        request.getSession().setAttribute(LESSON_CHOICES, new ArrayList<>());
    }

    public ArrayList<String> getChoices(HttpServletRequest request){
        return (ArrayList<String>) request.getSession().getAttribute(LESSON_CHOICES);
    }

    public boolean checkIfChoicesPresent(HttpServletRequest request){
        return request.getSession().getAttribute(LESSON_CHOICES) != null;
    }

    public void removeLessonsAttributeFromSession(HttpServletRequest request){
        request.getSession().removeAttribute(LESSON_CHOICES);

    }
}
