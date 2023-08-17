package com.example.mybookshopapp.controllers;

import com.example.mybookshopapp.exceptions.EmptySearchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(EmptySearchException exception, RedirectAttributes redirectAttributes){
        Logger.getLogger(this.getClass().getName()).warning(exception.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", exception);
        return "redirect:/";
    }
}
