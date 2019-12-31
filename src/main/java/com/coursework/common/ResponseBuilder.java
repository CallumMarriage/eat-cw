package com.coursework.common;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@NoArgsConstructor
public class ResponseBuilder {

    public void writeMessageToResponse(HttpServletResponse response, String message) {
        response.setContentType("application/xml");
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<?xml version='1.0' encoding='UTF-8'?> \n" + message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeErrorMessageToResponse(HttpServletResponse response, String message, Integer code) {
        writeMessageToResponse(response, "<error>\n" +
                "<message>" + message + "</message>\n" +
                "<code>" + code + "</code>\n"+
                "</error>");
    }
}
