package com.coursework.lessons;

import com.coursework.common.ResponseBuilder;
import com.coursework.common.SessionHandler;
import com.coursework.lessons.exception.LessonBookedException;
import com.coursework.lessons.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    private final ResponseBuilder responseBuilder;

    private final SessionHandler sessionHandler;

    public LessonController(LessonService lessonService, ResponseBuilder responseBuilder, SessionHandler sessionHandler) {
        this.lessonService = lessonService;
        this.responseBuilder = responseBuilder;
        this.sessionHandler = sessionHandler;
    }


    @GetMapping("/viewTimetable")
    public String viewTimetable(ModelMap model, HttpServletRequest request) {

        if (sessionHandler.checkIfLoggedIn(request)) {
            model.put("lessons", lessonService.getAllLessons());
            return "viewTimetable";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/viewSelection")
    public String viewSelection(ModelMap model, HttpServletRequest request) {

        if (sessionHandler.checkIfLoggedIn(request)) {


            if(!sessionHandler.checkIfChoicesPresent(request)){
                return "redirect:/viewTimetable";
            }

            model.put("myLessons", lessonService.getAllLessonsByMultipleIds(sessionHandler.getChoices(request)));

            return "viewSelection";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/chooseLesson")
    public String chooseLesson(HttpServletRequest request, HttpServletResponse response) {
        if (sessionHandler.checkIfLoggedIn(request)) {

            String lessonId = sessionHandler.getLessonIdFromSession(request);

            if (!sessionHandler.checkIfChoicesPresent(request)) {
                sessionHandler.createLessonChoicesAttributeInSession(request);
            }

            ArrayList<String> lessonsChoices = sessionHandler.getChoices(request);

            if (lessonsChoices.contains(lessonId)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseBuilder.writeErrorMessageToResponse(response, "You have already booked this lesson", 6);
            }

            lessonsChoices.add(lessonId);
            request.getSession().setAttribute("lessonChoices", lessonsChoices);

            return "redirect:/viewSelection";

        }

        return "redirect:/";
    }


    @PostMapping("/finaliseBooking")
    public String finaliseBooking(HttpServletRequest request, HttpServletResponse response) throws LessonBookedException {


        if (sessionHandler.checkIfLoggedIn(request)) {

            Integer clientId = sessionHandler.getClientIdFromSession(request);

            try {
                lessonService.removeCurrentLessonsForClient(clientId);

                if (!sessionHandler.checkIfChoicesPresent(request)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return "redirect:/viewSelection";
                }

                ArrayList<String> lessonChoices = sessionHandler.getChoices(request);

                if(lessonChoices.size() == 0){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return "viewSelection";
                }

                lessonService.finaliseBookingForClient(clientId, lessonChoices);
                sessionHandler.removeLessonsAttributeFromSession(request);

                response.setStatus(HttpServletResponse.SC_OK);
                return "redirect:/viewTimetable";

            } catch (Exception e) {
                log.error(e.getMessage());
                throw new LessonBookedException(e.getMessage());
            }
        }
        return "redirect:/";

    }

    @DeleteMapping("/removeLessonChoice/{lessonId}")
    public String removeLessonChoice(@PathVariable("lessonId") String lessonId, HttpServletRequest request) {
        if (sessionHandler.checkIfLoggedIn(request)) {

            if (!sessionHandler.checkIfChoicesPresent(request)) {
                return "redirect:viewTimetable";
            }
            sessionHandler.removeLessonFromSession(request, lessonId);

            return "redirect:/viewTimetable";
        } else {
            return "redirect:/";
        }
    }


}