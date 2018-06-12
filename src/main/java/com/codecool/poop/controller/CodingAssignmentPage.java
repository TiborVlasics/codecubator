package com.codecool.poop.controller;

import com.codecool.poop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CodingAssignmentPage extends HttpServlet implements LoginHandler {

    public CodingAssignmentPage(){
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session;
        session = request.getSession();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        if (!isUserLoggedIn(session)){
            response.sendRedirect("/");
            return;
        }

        engine.process("assignments/coding_assignment.html", context, response.getWriter());
    }

}
