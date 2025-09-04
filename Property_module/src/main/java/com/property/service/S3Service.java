package com.property.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	@Autowired
	private S3Client s3Client;

	@Value("${aws.credentials.bucket}")
	private String bucket;

	@Value("${aws.credentials.region}")
	private String region;

	public List<String> uploadFiles(MultipartFile[] files) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile file : files) {
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			try {
				// Build the request
				PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(fileName)
						.contentType(file.getContentType()).build();

				// Upload the file
				s3Client.putObject(putObjectRequest,
						RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

				// Build file URL manually (public bucket OR if you have correct permissions)
				String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
				urls.add(url);
			} catch (IOException e) {
				throw new RuntimeException("Error uploading file to S3", e);
			}
		}
		return urls;
	}

}
