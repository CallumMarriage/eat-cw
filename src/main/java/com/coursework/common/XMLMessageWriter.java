package com.coursework.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class XMLMessageWriter {

    public static HttpServletResponse writeMessageToResponse(HttpServletResponse response, String message) {
        response.setContentType("application/xml");
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<?xml version='1.0' encoding='UTF-8'?> \n" + message);
            writer.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
