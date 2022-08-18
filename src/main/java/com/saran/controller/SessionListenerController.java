package com.saran.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class SessionListenerController {

    private static final Logger LOG = LoggerFactory.getLogger(SessionListenerController.class);

    @GetMapping("/create-new-session")
    public String createNewSession(HttpServletRequest request, HttpServletResponse response) {

        HttpSession sessionObj = request.getSession(false);
        //check session exist or not
        //if not available create new session
        if (sessionObj == null) {
            LOG.info("Session not available, creating new session.");
            sessionObj = request.getSession(true);
        }
        String activeSessions = sessionObj.getAttribute("activeSessions")!=null
                ?sessionObj.getAttribute("activeSessions").toString()
                :"0";
        return "Session is available now with total active sessions : "+activeSessions;
    }

    @GetMapping("/destroy-active-session")
    public String removeSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessionObj = request.getSession(false);

        if (sessionObj != null) {
            sessionObj.invalidate();

            return "Session destroyed, now there are no active sessions.";
        }
        return "Session not available to destroy.";
    }
}
