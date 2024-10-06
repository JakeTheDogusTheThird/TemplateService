package com.example.TemplateService;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/templates")
@RestController
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<byte[]> getFile(TemplateEntity template) {
        HttpHeaders headers = new HttpHeaders();
        Blob QueriedBlob = templateService.getQueriedFile(template);

        headers.add("Content-Type", QueriedBlob.getContentType());
        return new ResponseEntity<>(QueriedBlob.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<byte[]> retrieveTemplate(
            @PathVariable final String name,
            @RequestParam(required = false) final String extensions,
            @RequestParam(required = false) final String locale) {
        final TemplateEntity template = TemplateEntity.builder(name)
                .fileExtension(extensions)
                .locale(locale)
                .build();
        final Blob file = this.templateService.getQueriedFile(template);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, file.getContentType());
        headers.add(HttpHeaders.CONTENT_LANGUAGE, file.getContentLanguage());

        return new ResponseEntity<>(file.getContent(), headers, HttpStatus.OK);
    }
//
//    @PostMapping
//    public ResponseEntity<byte[]> updateFile(TemplateEntity template) {
//        return new ResponseEntity<>();
//    }

}
