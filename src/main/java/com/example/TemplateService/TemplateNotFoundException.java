package com.example.TemplateService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TemplateNotFoundException extends RuntimeException{
    public TemplateNotFoundException(String errMessage) {
        super(errMessage);
    }

    public TemplateNotFoundException(TemplateEntity template) {
        super("Template not found" + template);
    }
}
