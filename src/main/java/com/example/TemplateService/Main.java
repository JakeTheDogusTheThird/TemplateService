package com.example.TemplateService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

//		Storage storage = StorageOptions
//				.newBuilder()
//				.setHost("http://localhost:4443")
//				.build()
//				.getService();
//
//		Blob blob = storage.get(BlobId.of("sample-bucket", "some_file.txt"));

//		System.out.println(blob.getGeneratedId());


	}

}
