package com.example.TemplateService;

import com.google.cloud.storage.BlobInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.cloud.storage.Blob;

@RequiredArgsConstructor
@RequestMapping("api/v1/template-service")
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
//
//    @PostMapping
//    public ResponseEntity<byte[]> updateFile(TemplateEntity template) {
//        return new ResponseEntity<>();
//    }

}
