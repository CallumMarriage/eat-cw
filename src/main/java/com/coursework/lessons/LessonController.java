package com.coursework.lessons;

import com.coursework.lessons.exception.LessonBookedException;
import com.coursework.lessons.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@Slf4j
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }


    @GetMapping("/viewTimetable")
    public String viewTimetable(ModelMap model, HttpServletRequest request) {

        if (checkIfLoggedIn(request)) {
            model.put("lessons", lessonService.getAllLessons());
            return "viewTimetable";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/viewSelection")
    public String viewSelection(ModelMap model, HttpServletRequest request) {

        if (checkIfLoggedIn(request)) {

            if(request.getSession().getAttribute("lessonChoices") == null){
                return "redirect:/viewTimetable";
            }

            Collection<String> lessonIds = (Collection<String>) request.getSession().getAttribute("lessonChoices");


            model.put("myLessons", lessonService.getAllLessonsByMultipleIds(lessonIds));

            return "viewSelection";
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/chooseLesson")
    public String chooseLesson(HttpServletRequest request) throws LessonBookedException{
        if (checkIfLoggedIn(request)) {

            String lessonId = request.getParameter("lessonId");

            if(request.getSession().getAttribute("lessonChoices") == null){
                request.getSession().setAttribute("lessonChoices", new ArrayList<>());
            }

            List<String> lessonChoices = (List<String>) request.getSession().getAttribute("lessonChoices");

            if(lessonChoices.contains(lessonId)){
                throw new LessonBookedException("You have already booked this lesson");
            }

            lessonChoices.add(lessonId);
            request.getSession().setAttribute("lessonChoices", lessonChoices);

            return "redirect:/viewSelection";

        }

        return "redirect:/";
    }


    @PostMapping("/finaliseBooking")
    public String finaliseBooking(HttpServletRequest request, HttpServletResponse response) throws LessonBookedException{


        if (checkIfLoggedIn(request)) {

            Integer clientId = Integer.parseInt(request.getParameter("clientId"));

            try {
                lessonService.removeCurrentLessonsForClient(clientId);

                if(request.getSession().getAttribute("lessonChoices") == null){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return "redirect:viewSelection";
                }
                lessonService.finaliseBookingForClient(clientId, (Collection<String>) request.getSession().getAttribute("lessonChoices"));
                request.getSession().removeAttribute("lessonChoices");

                response.setStatus(HttpServletResponse.SC_OK);
                return "redirect:/viewTimetable";

            } catch (Exception e) {
                log.error(e.getMessage());
                throw new LessonBookedException(e.getMessage());
            }
        }
        return "redirect:/";

    }

    private Boolean checkIfLoggedIn(HttpServletRequest request) {
        return "true".equals(request.getSession().getAttribute("isLoggedIn"));
    }

}