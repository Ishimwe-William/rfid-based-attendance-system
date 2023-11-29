package com.bunsen.rfidbasedattendancesystem.errors;

import exceptions.CustomDataNotFoundException;
import exceptions.CustomErrorException;
import exceptions.CustomParameterConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@Controller // Change annotation
@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(CustomDataNotFoundException.class)
    public String handleCustomDataNotFoundExceptions(Exception e, Model model) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        model.addAttribute("error", new ErrorResponse(status, e.getMessage(), stackTrace));
        return "error"; // Assuming you have an error.html template
    }

    @ExceptionHandler(CustomParameterConstraintException.class)
    public String handleCustomParameterConstraintExceptions(Exception e, Model model) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        model.addAttribute("error", new ErrorResponse(status, e.getMessage()));
        return "error"; // Assuming you have an error.html template
    }

    @ExceptionHandler(CustomErrorException.class)
    public String handleCustomErrorExceptions(Exception e, Model model) {
        // casting the generic Exception e to CustomErrorException
        CustomErrorException customErrorException = (CustomErrorException) e;

        HttpStatus status = customErrorException.getStatus();

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        customErrorException.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        model.addAttribute("error", new ErrorResponse(status, customErrorException.getMessage(), stackTrace, customErrorException.getData()));
        return "error"; // Assuming you have an error.html template
    }

    // fallback method
    @ExceptionHandler(Exception.class)
    public String handleExceptions(Exception e, Model model) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        model.addAttribute("error", new ErrorResponse(status, e.getMessage(), stackTrace));
        return "error"; // Assuming you have an error.html template
    }
}
