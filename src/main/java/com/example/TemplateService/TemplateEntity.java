package com.example.TemplateService;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Builder(builderMethodName = "hiddenBuilder")
@Setter
@Getter
public class TemplateEntity {
    private String fileName;
    private String fileExtension;
    private String locale;
    private String input;

    public static TemplateEntityBuilder builder(String fileName) {
        return hiddenBuilder().fileName(fileName);
    }
}
