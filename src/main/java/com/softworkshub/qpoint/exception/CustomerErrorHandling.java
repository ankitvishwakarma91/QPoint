package com.softworkshub.qpoint.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerErrorHandling implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);

        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("error", error);

        return "ErrorPage";
    }
}
